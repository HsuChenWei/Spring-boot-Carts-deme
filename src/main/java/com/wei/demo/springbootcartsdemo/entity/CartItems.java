package com.wei.demo.springbootcartsdemo.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cartItems")
public class CartItems {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.wei.demo.springbootcartsdemo.hibernate.SnowflakeIDGenerator")
    @Column(name = "cart_item_id")
    private String id;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "quantity")
    private int quantity;

}
