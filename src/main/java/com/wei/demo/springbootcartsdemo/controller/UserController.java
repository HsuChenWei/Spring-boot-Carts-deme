package com.wei.demo.springbootcartsdemo.controller;

import com.wei.demo.springbootcartsdemo.model.Security.TokenPair;
import com.wei.demo.springbootcartsdemo.model.User.CreateUser;
import com.wei.demo.springbootcartsdemo.model.User.UserDto;
import com.wei.demo.springbootcartsdemo.model.User.UserLogin;
import com.wei.demo.springbootcartsdemo.model.wrapper.RespWrapper;
import com.wei.demo.springbootcartsdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "會員註冊")
    @PostMapping("/register")
    public RespWrapper<UserDto> register(@Valid @RequestParam  CreateUser creation) throws IOException {

        return userService.createUser(creation)
                .map(user -> RespWrapper.success(modelMapper.map(user,UserDto.class))).get();
    }

    @Operation(summary = "取得 JWT 驗證登入")
    @PostMapping("login")
    public RespWrapper<TokenPair> login(@RequestBody UserLogin login) {
        Option<TokenPair> userOption = userService.login(login);
        return userOption
                .map(u -> modelMapper.map(u, TokenPair.class))
                .map(RespWrapper::success)
                .getOrElseThrow(() -> new RuntimeException("UserName or UserPassword is incorrect."));
    }
}
