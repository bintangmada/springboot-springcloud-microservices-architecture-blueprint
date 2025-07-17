package com.bintang.authentication_service.controller;

import com.bintang.authentication_service.dto.AuthRequest;
import com.bintang.authentication_service.entity.UserInfo;
import com.bintang.authentication_service.service.JwtService;
import com.bintang.authentication_service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        //cek apakah user sudah terdaftar di database atau tidak sebelum generate token
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getName());
        }else{
            throw new RuntimeException("Username or password incorrect");
        }
    }

    @GetMapping("/validate-token")
    public String validateToken(@RequestParam String token){
        return jwtService.validateToken(token);
    }

}
