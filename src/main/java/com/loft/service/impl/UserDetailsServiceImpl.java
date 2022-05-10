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
    public UserDetails loadUserByUsername(String emailAdress) throws UsernameNotFoundException {

        final User userFromDB = userRepository.findByEmailAdress(emailAdress);
        if (userFromDB == null) {
            throw new UsernameNotFoundException("Email " + emailAdress + " not found in database!");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(userFromDB.getEmailAdress())
                .password(userFromDB.getPassword())
                .roles(userFromDB.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.joining()))
                .build();
    }

}
