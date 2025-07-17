package com.bintang.authentication_service.controller;

import com.bintang.authentication_service.dto.AuthRequest;
import com.bintang.authentication_service.entity.UserInfo;
import com.bintang.authentication_service.service.JwtService;
import com.bintang.authentication_service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String createUser(@RequestBody UserInfo userInfo){
        return userInfoService.createUser(userInfo);
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest authRequest){
        return jwtService.generateToken(authRequest.getName());
    }

}
