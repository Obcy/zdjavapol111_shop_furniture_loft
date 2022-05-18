package com.loft.service;

import com.loft.model.User;

public interface UserService {

    boolean existsByEmailAddress(String emailAddress);

    User findByEmailAddress(String emailAddress);

    void save(User user);
}
