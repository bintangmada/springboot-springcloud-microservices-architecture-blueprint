package com.bintang.api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    // kelas utility untuk mengecek apakah url nya diizinkan atau tidak

    public static final List<String> whiteListEndPoints = List.of(
            "/api/auth/register",
            "/api/auth/generate-token",
            "/api/auth/validate-token",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> whiteListEndPoints
                    .stream()
                    .noneMatch(uri -> request
                            .getURI()
                            .getPath()
                            .equals(uri));


}
