package com.bintang.api_gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    @Autowired
    private RouteValidator routeValidator;
    public AuthFilter(){
        super(NameConfig.class);
    }

    @Autowired
    private RestTemplate restTemplate;

    public static class Config{

    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) -> {

            // HANYA jika URL bukan whitelist, baru kita cek header + token
            if (routeValidator.isSecured.test(exchange.getRequest())) {

                // 1. HARUS ADA HEADER
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                List<String> authHeaderValues = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
                if (authHeaderValues != null && !authHeaderValues.isEmpty()) {
                    String token = authHeaderValues.get(0);
                    if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7);
                    }

                    // 2. VALIDASI KE AUTH SERVICE
                    try {
                        // tidak bisa kalau pake nama service yang ada di eureka. harus service nya langsung
                        restTemplate.getForObject("http://localhost:8099/api/auth/validate-token?token=" + token, String.class);
                    } catch (Exception e) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }

                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }

            return chain.filter(exchange);
        };
    }

}
