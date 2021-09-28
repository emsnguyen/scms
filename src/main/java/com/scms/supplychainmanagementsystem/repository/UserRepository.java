package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String usernameÒ);

}
