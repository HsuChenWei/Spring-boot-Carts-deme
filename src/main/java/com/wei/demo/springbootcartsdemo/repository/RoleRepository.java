package com.wei.demo.springbootcartsdemo.repository;

import com.wei.demo.springbootcartsdemo.entity.Role;
import com.wei.demo.springbootcartsdemo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
