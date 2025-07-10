package com.bintang.order_service;

import com.bintang.order_service.webclient.CustomerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
		System.out.println("\nSERVER IS RUNNING");
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Autowired
	private LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;
	@Bean
	WebClient webCLient(){
		return WebClient.builder()
				.baseUrl("http://CUSTOMER-SERVICE")
				.filter(loadBalancedExchangeFilterFunction)
				.build();
	}

	@Bean
	CustomerClient customerClient(){
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webCLient()))
				.build();
		return factory.createClient(CustomerClient.class);
	}


}
