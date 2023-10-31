package com.wei.demo.springbootcartsdemo.service;

import com.wei.demo.springbootcartsdemo.entity.User;
import com.wei.demo.springbootcartsdemo.model.Security.TokenPair;
import com.wei.demo.springbootcartsdemo.model.User.CreateUser;
import com.wei.demo.springbootcartsdemo.model.User.UserLogin;
import io.vavr.control.Option;

import java.util.List;

public interface UserService {


    Option<TokenPair> login(UserLogin login);

    Option<User> createUser(CreateUser creation);

    Option<User> getUserByUserName(String userName);

    Option<User> getUserById(String id);

    List<String> getRoleIdsForUser (String userId);

}
