package com.springjwt.security.service;

import com.springjwt.security.exception.BadRequestException;
import com.springjwt.security.exception.NotFoundException;
import com.springjwt.security.model.ERole;
import com.springjwt.security.model.Role;
import com.springjwt.security.model.User;
import com.springjwt.security.payload.Response.JwtResponse;
import com.springjwt.security.repository.RoleRepository;
import com.springjwt.security.repository.UserRepository;
import com.springjwt.security.security.jwt.AuthTokenFilter;
import com.springjwt.security.security.jwt.JwtUtils;
import com.springjwt.security.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthTokenFilter authTokenFilter;

    @Autowired
    JwtUtils jwtUtils;

    public JwtResponse login(User user) {

        if (!userRepository.existsByUsername(user.getUsername())) {
            throw new NotFoundException("This user is not registered.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);

    }

    public String register(User user) throws RuntimeException {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new BadRequestException("Username should be provided.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new BadRequestException("Email should be provided.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new BadRequestException("Password should be provided.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadRequestException("Username is already used.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email is already used.");
        }

        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> strRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new NotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            Role adminRole = roleRepository.findByName(ERole.ADMIN)
                    .orElseThrow(() -> new NotFoundException("Error: Role is not found."));
            roles.add(adminRole);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully!";
    }

    public Optional<User> retrieveMyProfile(HttpServletRequest httpServletRequest) {
        return userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(authTokenFilter.parseJwt(httpServletRequest)));
    }

}