package com.bintang.authentication_service.service;

import com.bintang.authentication_service.entity.UserInfo;
import com.bintang.authentication_service.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(UserInfo userInfo){

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword())); // ENCODE PASSWORD SAAT CREATE USER
        userInfoRepository.save(userInfo);
        return "User added successfully";
    }
}
