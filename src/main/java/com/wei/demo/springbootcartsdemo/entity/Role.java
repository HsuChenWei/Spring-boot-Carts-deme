package com.wei.demo.springbootcartsdemo.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "role_id")
    private String id;

    @Column(name = "role_name")
    private String roleName;

}
