package org.baattezu.userservice.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.client.MembershipClient;
import org.baattezu.userservice.dto.response.UserInfoPlusMembershipInfo;
import org.baattezu.userservice.dto.response.UserInfoResponse;
import org.baattezu.userservice.dto.response.UserMembershipResponse;
import org.baattezu.userservice.dto.response.UserMembershipWithId;
import org.baattezu.userservice.exception.UserNotFoundException;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.dto.request.UserInfoRequest;
import org.baattezu.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final MembershipClient membershipClient;


    private UserInfo findUserInfoById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User not found with id : %d", id ))
                );
    }
    private void checkUserExistsAlready(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException(String.format("User already exists with email: %s", email));
        }
    }

    private void checkUserExists(Long id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(
                    String.format("User does not exist with id : %s", id )
                );
        }
    }
    public boolean checkUserExistsForMicroservices(Long id) {
        return userRepository.existsById(id);
    }
    public String getEmailForMicroservice(Long id){
        return findUserInfoById(id).getEmail();
    }

    public List<UserInfo> getUsers(){
        return userRepository.findAll();
    }



    public UserInfoResponse getUserInfoResponse(Long id){
        UserInfo userInfo = findUserInfoById(id);
        UserMembershipResponse membership = membershipClient.getUserMembership(id);
        return UserInfoResponse.builder()
                .email(userInfo.getEmail())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .birthDate(userInfo.getBirthDate())
                .phoneNumber(userInfo.getPhoneNumber())
                .membership(membership)
                .build();
    }
    public List<UserInfoPlusMembershipInfo> getUserInfosResponse(){
        List<UserInfo> userInfos = userRepository.findAll();
        List<UserMembershipWithId> memberships = membershipClient.getUsersMemberships();
        return userInfos.stream()
                .map(u -> getUserInfoPlusMembershipInfo(u, memberships)).toList();
    }

    private UserInfoPlusMembershipInfo getUserInfoPlusMembershipInfo(UserInfo userInfo, List<UserMembershipWithId> memberships) {
        Long uiId = userInfo.getId();
        UserMembershipWithId userMembership = memberships.stream()
                .filter(membership -> membership.getUserId().equals(uiId))
                .findAny()
                .orElse(new UserMembershipWithId(uiId, LocalDate.EPOCH, false));
        return new UserInfoPlusMembershipInfo(
                userInfo.getId(),
                userInfo.getEmail(),
                userInfo.getPhoneNumber(),
                userMembership.getEndDate(),
                userMembership.getIsFrozen()
        );
    }

    public UserInfo updateUserInfo(Long id, UserInfoRequest newUserInfo) {
        checkUserExists(id);
        UserInfo userInfo = findUserInfoById(id);
        logger.info("Updating user with email: {}", userInfo.getEmail());

        setIfNotNull(newUserInfo.getFirstName(), userInfo::setFirstName);
        setIfNotNull(newUserInfo.getLastName(), userInfo::setLastName);
        setIfNotNull(newUserInfo.getPhoneNumber(), userInfo::setPhoneNumber);
        setIfNotNull(newUserInfo.getBirthDate(), userInfo::setBirthDate);

        userRepository.save(userInfo);

        logger.info("User updated successfully with email: {}", userInfo.getEmail());
        return userInfo;
    }
    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }




    public void deleteUserInfo(Long userId) {
        deleteUserInMembershipService(userId);;
        deleteUserFromRepository(userId);
    }

    private void deleteUserInMembershipService(Long userId) {
        try {
            ResponseEntity<Void> response = membershipClient.deleteUserMembership(userId);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new IllegalStateException("Failed to delete user in <MEMBERSHIP-SERVICE>. User deletion aborted.");
            }
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                throw new IllegalStateException("Failed to delete user in <MEMBERSHIP-SERVICE>. User deletion aborted.");
            }
            throw e;
        }
    }

    private void deleteUserFromRepository(Long userId) {
        userRepository.deleteById(userId);
    }

    public void saveUser(String email) {
        checkUserExistsAlready(email);
        userRepository.save(new UserInfo(email));
    }

}
