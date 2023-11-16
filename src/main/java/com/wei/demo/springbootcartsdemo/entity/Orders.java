package com.wei.demo.springbootcartsdemo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "orders")
public class Orders {
}
