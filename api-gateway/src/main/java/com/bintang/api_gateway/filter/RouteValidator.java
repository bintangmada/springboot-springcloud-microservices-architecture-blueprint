package com.bintang.api_gateway.filter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {
    // kelas utility untuk mengecek apakah url nya diizinkan atau tidak

    public static final List<String> whiteListEndPoints = List.of(
            "/api/auth/register",
            "/api/auth/generate-token",
            "/api/auth/validate-token",
            "/eureka"
    );


}
