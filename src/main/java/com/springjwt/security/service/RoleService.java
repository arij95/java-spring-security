package com.springjwt.security.service;

import com.springjwt.security.model.ERole;
import com.springjwt.security.model.Role;
import com.springjwt.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleService {

    private static final ERole USER_ROLE = ERole.USER;
    private static final ERole ADMIN_ROLE = ERole.ADMIN;

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void insertRoles() {

        if(!roleRepository.findByName(USER_ROLE).isPresent()) {
            Role userRole = new Role();
            userRole.setName(ERole.USER);
            roleRepository.save(userRole);
        }

        if(!roleRepository.findByName(ADMIN_ROLE).isPresent()) {
            Role adminRole = new Role();
            adminRole.setName(ERole.ADMIN);
            roleRepository.save(adminRole);
        }
    }

}
