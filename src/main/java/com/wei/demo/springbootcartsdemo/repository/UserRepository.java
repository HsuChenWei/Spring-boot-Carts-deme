package com.wei.demo.springbootcartsdemo.repository;

import com.wei.demo.springbootcartsdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserName(String userName);
}
