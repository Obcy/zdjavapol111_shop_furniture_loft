package com.loft.repository;

import com.loft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAdress(String emailAdress);

    boolean existsByEmailAdress(String emailAdress);


}
