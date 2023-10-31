package com.wei.demo.springbootcartsdemo.entity;


import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wei.demo.springbootcartsdemo.hibernate.SnowflakeIDGenerator")
    @Column(name = "user_id")
    private String id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pwd")
    private String userPwd;

    @Column(name = "email")
    private String email;

    @Column(name = "create_at")
    private LocalTime createAt;

    @Column(name = "update_at")
    private LocalTime updateAt;

    @Column(name = "delete_at")
    private LocalTime deleteAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<UserRole> userRoles = new ArrayList<>();
}
