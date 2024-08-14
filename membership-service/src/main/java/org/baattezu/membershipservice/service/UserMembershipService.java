package org.baattezu.membershipservice.service;


import org.baattezu.membershipservice.dto.*;
import lombok.RequiredArgsConstructor;
import org.baattezu.membershipservice.client.UserServiceClient;
import org.baattezu.membershipservice.dto.response.UserMembershipResponse;
import org.baattezu.membershipservice.entities.Membership;
import org.baattezu.membershipservice.entities.UserMembership;
import org.baattezu.membershipservice.entities.UserMembershipId;
import org.baattezu.membershipservice.exception.FreezeException;
import org.baattezu.membershipservice.exception.NotFoundException;
import org.baattezu.membershipservice.repository.MembershipRepository;
import org.baattezu.membershipservice.repository.UserMembershipRepository;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class UserMembershipService {

    private final UserMembershipRepository userMembershipRepository;
    private final MembershipRepository membershipRepository;
    private final UserServiceClient userServiceClient;
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

    private Membership findMembershipById(Long membershipId){
        return membershipRepository.findById(membershipId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Membership not found with id: %d", membershipId)
                ));
    }
    private UserMembership findUserMembershipById(Long userId){
        return userMembershipRepository.findUserMembershipById_UserId(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User not found with id: %d", userId)
                ));
    }
    private String calculateRemainingDays(LocalDateTime endDate){
        Duration duration = Duration.between(LocalDateTime.now(), endDate);
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format(" %d days, %02d:%02d:%02d\n", days, hours, minutes, seconds);
    }

    private String formatDate(LocalDate date){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(dateTimeFormatter);
    }
    private void isFrozen(
            LocalDate now,
            LocalDate frozenUntil
    ){
        if (now.isBefore(frozenUntil)) {
            throw new FreezeException(String.format(
                    "Membership is frozen already: until %s, wait %s",
                    formatDate(frozenUntil),
                    calculateRemainingDays(frozenUntil.atStartOfDay()))
            );
        }
    }
    private void isNotFrozen(
            LocalDate now,
            LocalDate frozenUntil
    ){
        if (now.isAfter(frozenUntil)) {
            throw new FreezeException(
                    "Membership is not frozen"
            );
        }
    }
    private void validateFreezeDate(
            LocalDate freezeUntil,
            LocalDate now,
            LocalDate endDate
    ) {
        if (freezeUntil.isBefore(now)) {
            throw new FreezeException(String.format(
                    "Freeze date is before current date: %s -> %s",
                    formatDate(freezeUntil), formatDate(now))
            );
        }

        if (freezeUntil.isAfter(endDate)) {
            throw new FreezeException(String.format(
                    "Freeze date is after end date: %s -> %s",
                    formatDate(freezeUntil), formatDate(endDate))
            );
        }
    }

    private boolean checkUserExists(Long userId){
        return userServiceClient.checkUserExists(userId);
    }

    private void sendMessage(String email, String message){
        EmailMessage emailMessage = new EmailMessage(email, message);
        kafkaTemplate.send("membership-events", emailMessage);
    }

    /* todo получение информации о абонементе
        1) заполняем отдельный класс вместе с нешаблонными данными
        в которых указано количество дней до конца абонемента и сформатированная дата конца
        2) в зависимости от того заморожен ли абонемент мы добавляем заморозку или нет
        null нету
        дата(12.12.2012) есть
     */

    public UserMembershipResponse getUserMembershipInfoResponse(Long userId){
        UserMembership userMembership = findUserMembershipById(userId);
        Membership membership = findMembershipById(userMembership.getId().getMembershipId());
        UserMembershipResponse response = UserMembershipResponse.builder()
                .name(membership.getName())
                .endDate(formatDate(userMembership.getEndDate()))
                .remainingDays(calculateRemainingDays(
                        userMembership.getEndDate().atTime(23, 59, 0)
                ))
                .build();

        if (LocalDate.now().isBefore(userMembership.getFrozenUntil())){
            response.setFrozenUntilDate(
                    formatDate(userMembership.getFrozenUntil())
            );
        }
        return response;
    }
    /* todo добавление абонемента к пользователю
        1) проверка на существующий абонемент
        2) в зависимости от существования либо добавляем дни в абонемент или создаем новый
        3) при создании нового просто заполняем все даты
     */


    public UserMembership addMembershipToUser(Long userId, Long membershipId){
        checkUserExists(userId);
        Membership membership = findMembershipById(membershipId);
        UserMembership userMembership = userMembershipRepository.findUserMembershipById_UserId(userId)
                .orElse(null);

        if (userMembership != null) {
            extendMembership(userMembership, membership);
        } else {
            userMembership = createNewMembership(userId, membershipId, membership);
        }

        String message = String.format("Your got new \"%s\" membership at : %s\n \"%s\" membership includes %d months of unlimited access," +
                        "Description: %s\nYour new membership ends at: %s",
                membership.getName(), formatDate(LocalDate.now()), membership.getName(),
                membership.getMonths(), membership.getDescription(), formatDate(userMembership.getEndDate()));
        sendMessage(userServiceClient.getEmail(userId), message);

        return userMembershipRepository.save(userMembership);
    }

    private UserMembership createNewMembership(Long userId, Long membershipId, Membership membership) {
        UserMembership userMembership;
        userMembership = new UserMembership();

        UserMembershipId userMembershipId = new UserMembershipId(userId, membershipId);
        userMembership.setId(userMembershipId);

        LocalDate newDate = LocalDateTime.now().toLocalDate();
        userMembership.setStartDate(newDate);
        userMembership.setEndDate(
                newDate.plusMonths(membership.getMonths())
        );
        userMembership.setFrozenUntil(newDate.minusDays(1));
        userMembership.setLastFreeze(newDate.minusDays(1));
        return userMembership;
    }

    private void extendMembership(UserMembership userMembership, Membership membership) {
        LocalDate endDate = userMembership.getEndDate()
                .plusMonths(membership.getMonths());
        userMembership.setEndDate(endDate);
    }

    public void deleteUserMembership(Long userId) {
        if (checkUserExists(userId)){
            userMembershipRepository.deleteUserMembershipById_UserId(userId);
            sendMessage(userServiceClient.getEmail(userId),"We are sorry, but your membership got cancelled");
        } else {
            throw new NotFoundException("User not found with id: " + userId);
        }
    }


    /* todo make freezeMembership / сделать заморозку абонемента
        1) беру id и желаемую дату заморозки ,и объявляю данные для работы
        2) проверяю заморожен ли уже абонемент
        3) проверяю правильная ли дата в контексте заморозки (не раньше начала, не позже конца абонемента)
        4) считаю дни с начала (сейчас) и до конца заморозки и добавляю их в общее количество дней после заморозки
     */
    public UserMembership freezeMembership(
            Long userId,
            LocalDate freezeUntil
    ) {
        UserMembership userMembership = findUserMembershipById(userId);
        LocalDate now = LocalDate.now();
        LocalDate frozenUntil = userMembership.getFrozenUntil();
        LocalDate endDate = userMembership.getEndDate();

        isFrozen(now, frozenUntil);
        validateFreezeDate(freezeUntil, now, endDate);

        unfreezeMembership(userMembership, now, frozenUntil, endDate);

        long addedDays = DAYS.between(now, freezeUntil);
        userMembership.setEndDate(endDate.plusDays(addedDays));

        userMembership.setFrozenUntil(freezeUntil);
        userMembership.setLastFreeze(freezeUntil);



        String message = "Your membership has been frozen at : " + formatDate(now) +
                "\nUntil:" + formatDate(userMembership.getFrozenUntil()) +
                "\nYour membership ends at: " + formatDate(userMembership.getEndDate());
        sendMessage(userServiceClient.getEmail(userId), message);


        return userMembershipRepository.save(userMembership);
    }

    // todo вспомогательный метод для разморозки в
    //  котором мы считаем дни с разморозки до заморозки
    //  и отнимаем их с общего количества дней
    private void unfreezeMembership(
            UserMembership userMembership,
            LocalDate now,
            LocalDate frozenUntil,
            LocalDate endDate
    ){
        long subtractedDays = DAYS.between(now, frozenUntil);
        userMembership.setEndDate(endDate.minusDays(subtractedDays));

        userMembership.setFrozenUntil(now);
        userMembership.setLastFreeze(now);
    }
    // todo метод для контроллера
    public UserMembership unfreezeMembership(Long userId) {
        UserMembership userMembership = findUserMembershipById(userId);
        LocalDate now = LocalDate.now();
        LocalDate frozenUntil = userMembership.getFrozenUntil();
        LocalDate endDate = userMembership.getEndDate();

        isNotFrozen(now, frozenUntil);

        unfreezeMembership(userMembership, now, frozenUntil, endDate);


        String message = "Your membership has been unfrozen at " + formatDate(now) + "\n" +
                "Your membership ends at " + userMembership.getEndDate();
        sendMessage(userServiceClient.getEmail(userId), message);


        return userMembershipRepository.save(userMembership);
    }

}
