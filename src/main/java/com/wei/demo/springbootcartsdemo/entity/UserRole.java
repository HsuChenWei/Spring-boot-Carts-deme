package com.wei.demo.springbootcartsdemo.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id", insertable = true,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = true, updatable = false)
    private Role role;

}
