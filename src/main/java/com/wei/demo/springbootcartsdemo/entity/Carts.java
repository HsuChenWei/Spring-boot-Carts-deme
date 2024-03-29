package com.wei.demo.springbootcartsdemo.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "carts")
public class Carts {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator",strategy = "com.wei.demo.springbootcartsdemo.hibernate.SnowflakeIDGenerator")
    @Column(name = "cart_id")
    private String id;

    @Column(name = "user_id")
    private String userId;

}
