package com.example.employee.persistence;

import com.example.employee.domain.UserAndRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserAndRole, Long> {

    Optional<UserAndRole> findByUsername(String username);

}
