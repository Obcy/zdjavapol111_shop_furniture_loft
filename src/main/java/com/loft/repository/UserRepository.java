package com.loft.repository;

import com.loft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);


}
