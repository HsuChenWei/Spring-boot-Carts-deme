package com.wei.demo.springbootcartsdemo.repository;

import com.wei.demo.springbootcartsdemo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
}
