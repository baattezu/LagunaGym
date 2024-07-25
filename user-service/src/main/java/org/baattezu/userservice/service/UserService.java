package org.baattezu.userservice.service;

import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.client.MembershipClient;
import org.baattezu.userservice.dto.response.UserInfoResponse;
import org.baattezu.userservice.dto.response.UserMembershipResponse;
import org.baattezu.userservice.exception.UserNotFoundException;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.dto.request.UserInfoRequest;
import org.baattezu.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

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

    public void checkUserExists(Long id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(
                    String.format("User does not exists with id : %s", id )
                );
        }
    }

    public List<UserInfo> getUsers(){
        return userRepository.findAll();
    }


    public String getEmail(Long id){
        checkUserExists(id);
        return findUserInfoById(id).getEmail();
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

    public UserInfo updateUserInfo(Long id, UserInfoRequest newUserInfo) {
        checkUserExists(id);

        UserInfo userInfo = findUserInfoById(id);
        logger.info("Updating user with email: {}", userInfo.getEmail());

        setIfNotNull(newUserInfo.getFirstName(), userInfo::setFirstName);
        setIfNotNull(newUserInfo.getLastName(), userInfo::setLastName);
        setIfNotNull(newUserInfo.getPhoneNumber(), userInfo::setPhoneNumber);
        setIfNotNull(newUserInfo.getBirthDate(), userInfo::setBirthDate);

        UserInfo updatedUser = userRepository.save(userInfo);

        logger.info("User updated successfully with email: {}", userInfo.getEmail());
        return updatedUser;
    }
    private <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }


    public void deleteUserInfo(Long id) {
        try{
            membershipClient.deleteUserMembership(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found with id: "+ id);
        }
        userRepository.deleteById(id);
    }


    public void saveUser(String email) {
        checkUserExistsAlready(email);
        userRepository.save(new UserInfo(email));
    }

}
