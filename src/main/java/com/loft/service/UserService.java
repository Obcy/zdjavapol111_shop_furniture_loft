package com.loft.service;

import com.loft.model.User;

public interface UserService {

    boolean existsByEmailAdress(String emailAdress);

    void save(User user);
}
