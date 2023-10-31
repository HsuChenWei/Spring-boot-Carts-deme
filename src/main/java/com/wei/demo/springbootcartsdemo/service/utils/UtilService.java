package com.wei.demo.springbootcartsdemo.service.utils;


import com.wei.demo.springbootcartsdemo.model.Security.TokenPair;
import io.vavr.control.Option;

public interface UtilService {

    Option<TokenPair> generateTokenPair(String id);
}
