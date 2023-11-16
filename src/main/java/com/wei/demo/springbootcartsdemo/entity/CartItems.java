package com.wei.demo.springbootcartsdemo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "cartItems")
public class CartItems {
    @Id
    private String id;
    private String cartId;
    private String productId;
    private int quantity;

}
