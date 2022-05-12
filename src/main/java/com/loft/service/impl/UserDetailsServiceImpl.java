package com.loft.service.impl;

import com.loft.model.Role;
import com.loft.model.User;
import com.loft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {

        final User userFromDB = userRepository.findByEmailAddress(emailAddress);
        if (userFromDB == null) {
            throw new UsernameNotFoundException("Email " + emailAddress + " not found in database!");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(userFromDB.getEmailAddress())
                .password(userFromDB.getPassword())
                .roles(userFromDB.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.joining()))
                .build();
    }

}
