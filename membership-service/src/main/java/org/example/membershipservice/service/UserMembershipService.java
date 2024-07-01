package org.example.membershipservice.service;



import lombok.RequiredArgsConstructor;
import org.example.membershipservice.client.UserServiceClient;
import org.example.membershipservice.dto.UserMembershipResponse;
import org.example.membershipservice.entities.Membership;
import org.example.membershipservice.entities.UserMembership;
import org.example.membershipservice.entities.UserMembershipId;
import org.example.membershipservice.exception.FreezeException;
import org.example.membershipservice.exception.NotFoundException;
import org.example.membershipservice.repository.MembershipRepository;
import org.example.membershipservice.repository.UserMembershipRepository;

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
                    calculateRemainingDays(now.atTime(LocalTime.now())))
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
        Membership membership = findMembershipById(membershipId);
        UserMembership userMembership = userMembershipRepository.findUserMembershipById_UserId(userId)
                .orElse(null);

        if (userMembership != null) {
            LocalDate endDate = userMembership.getEndDate()
                    .plusMonths(membership.getMonths());
            userMembership.setEndDate(endDate);
        } else {
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
        }
        return userMembershipRepository.save(userMembership);
    }

    public List<UserMembership> getAllUserMemberships() {
        return userMembershipRepository.findAll();
    }


    public void deleteUserMembership(UserMembershipId id) {
        userMembershipRepository.deleteById(id);
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

        return userMembershipRepository.save(userMembership);
    }
}
