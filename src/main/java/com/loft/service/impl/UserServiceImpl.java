package com.loft.service.impl;

import com.loft.model.Role;
import com.loft.model.User;
import com.loft.repository.RoleRepository;
import com.loft.repository.UserRepository;
import com.loft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public boolean existsByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is empty!");
        }

        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        user.setPassword(getEncodedPassword(user.getPassword()));
        user.setRoles(getUserRoles());
        userRepository.save(user);
    }

    private String getEncodedPassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    private List<Role> getUserRoles() {
        final Role roleUser = roleRepository.findByName("USER");
        return List.of(roleUser);
    }


}
