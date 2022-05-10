package com.loft.service;

import com.loft.model.User;

public interface UserService {

    boolean existsByUsername(String username);

    void save(User user);
}
