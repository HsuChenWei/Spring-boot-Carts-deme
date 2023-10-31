package com.wei.demo.springbootcartsdemo.model.User;

import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateUser {

    private String userName;

    private String userPwd;

    private String email;

    private LocalTime createAt;

}
