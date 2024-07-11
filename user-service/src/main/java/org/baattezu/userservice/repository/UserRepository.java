package org.baattezu.userservice.repository;

import org.baattezu.userservice.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findUserInfoByEmail(String email);
    boolean existsByEmail(String email);
}
