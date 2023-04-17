package com.springjwt.security.repository;

import com.springjwt.security.model.ERole;
import com.springjwt.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
