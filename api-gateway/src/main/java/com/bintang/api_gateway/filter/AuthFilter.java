package com.bintang.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

public class AuthFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    public AuthFilter(){
        super(NameConfig.class);
    }

    public static class Config{

    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) ->{

            // cek request yang datang apakah punya header atau tidak


            return chain.filter(exchange);
        };
    }
}
