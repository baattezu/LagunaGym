package org.baattezu.userservice.service;

import lombok.RequiredArgsConstructor;
import org.baattezu.userservice.client.MembershipClient;
import org.baattezu.userservice.dto.MembershipRequest;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.dto.UserInfoRequest;
import org.baattezu.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final MembershipClient membershipClient;

    public UserInfo addMembershipToUser(Long userId, MembershipRequest membershipRequest) {
        UserInfo userInfo = getUserInfoById(userId);
        membershipClient.addMembershipToUser(userId, membershipRequest);
        return userInfo;
    }

    public UserInfo getUserInfoByEmail(String email) {
        return userRepository.findUserInfoByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User not found with email : %s", email ))
                );
    }
    public UserInfo getUserInfoById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User not found with id : %d", id ))
                );
    }

    public void checkUserExists(String email) {
        if (userRepository.findUserInfoByEmail(email).isPresent()) {
            throw new IllegalStateException(String.format("User already exists with email: %s", email));
        }
    }

    public List<UserInfo> getUsers(){
        return userRepository.findAll();
    }
    public UserInfo createUserInfo(UserInfo userInfo){
        checkUserExists(userInfo.getEmail());
        return userRepository.save(userInfo);
    }

    public UserInfo updateUserInfo(UserInfoRequest newUserInfo) {
        logger.info("Updating user with email: {}", newUserInfo.getEmail());
        UserInfo userInfo = getUserInfoByEmail(newUserInfo.getEmail());
        userInfo.setFirstName(newUserInfo.getFirstName());
        userInfo.setLastName(newUserInfo.getLastName());
        userInfo.setPhoneNumber(newUserInfo.getPhoneNumber());
        userInfo.setEmail(newUserInfo.getEmail());
        UserInfo updatedUser = userRepository.save(userInfo);
        logger.info("User updated successfully with email: {}", newUserInfo.getEmail());
        return updatedUser;
    }


    public void deleteUserInfo(Long id) {
        userRepository.deleteById(id);
    }




}
