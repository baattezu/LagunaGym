package com.lagunagym.LagunaGym.repositories;

import com.lagunagym.LagunaGym.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
