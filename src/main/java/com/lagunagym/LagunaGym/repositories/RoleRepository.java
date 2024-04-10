package com.lagunagym.LagunaGym.repositories;

import com.lagunagym.LagunaGym.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
}
