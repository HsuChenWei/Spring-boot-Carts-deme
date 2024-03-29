package com.wei.demo.springbootcartsdemo.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "orders")
public class Orders {


    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wei.demo.springbootcartsdemo.hibernate.SnowflakeIDGenerator")
    @Column(name = "order_id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
}
