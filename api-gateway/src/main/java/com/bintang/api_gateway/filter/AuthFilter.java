package com.bintang.api_gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
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
        return (exchange, chain) ->{

            // CEK APAKAH REQUEST YANG DATANG ADALAH BAGIAN DARI YANG SUDAH DI WHITE LIST.
            // KALAU BUKAN, MAKA CARI TOKEN DI DALAM HEADER YANG NAMANYA AUTHORIZATION
            // KALAU TIDAK ADA MAKA AKAN DIKASIH UNAUTHORIZED
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }

            List<String> authHeaderValues = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
            if(authHeaderValues != null && !authHeaderValues.isEmpty()){
                String token = authHeaderValues.get(0);
                if(token != null && token.startsWith("Bearer ")){
                    token = token.substring(7);
                }

                // validasi token
                try{
                    restTemplate.getForObject("http://authentication-service/api/auth/validate-token?token"+token, String.class);
                }catch (Exception e){
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}
