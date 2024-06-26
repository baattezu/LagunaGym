package org.baattezu.userservice.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.baattezu.userservice.model.UserInfo;
import org.baattezu.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserInfo> getUsers(){
        return userRepository.findAll();
    }

    public UserInfo getUserInfoByEmail(String email) throws NotFoundException {
        return userRepository.findUserInfoByEmail(email).orElseThrow(() -> new NotFoundException("User not found with this email: " + email));
    }
    public UserInfo getUserInfoById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserInfo createUserInfo(UserInfo userInfo) throws UserInfoExistsException {
        if (userRepository.findUserInfoByEmail(userInfo.getEmail()).isEmpty()){
            throw new UserInfoExistsException();
        }
        return userRepository.save(userInfo);
    }

    public UserInfo updateUserInfo(UserInfo userInfo) {
        return userRepository.save(userInfo);
    }

    public void deleteUserInfo(Long id) {
        userRepository.deleteById(id);
    }


}
