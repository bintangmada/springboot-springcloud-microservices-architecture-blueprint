package com.bintang.order_service;

import com.bintang.order_service.webclient.CustomerClient;
import com.bintang.order_service.webclient.ProductClient;
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
		System.out.println("\nORDER SERVICE IS RUNNING");
	}

//	@Bean
//	@LoadBalanced
//	RestTemplate restTemplate(){
//		return new RestTemplate();
//	}

	@Autowired
	private LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;

	@Autowired
	private WebClient.Builder webClientBuilderCustomer;

	@Autowired
	private WebClient.Builder webClientBuilderProduct;
	@Bean
	WebClient webCLientCustomer(){
		return webClientBuilderCustomer
				.baseUrl("http://CUSTOMER-SERVICE")
				.filter(loadBalancedExchangeFilterFunction)
				.build();
	}

	@Bean
	WebClient webCLientProduct(){
		return webClientBuilderProduct
				.baseUrl("http://PRODUCT-SERVICE")
				.filter(loadBalancedExchangeFilterFunction)
				.build();
	}

	@Bean
	CustomerClient customerClient(){
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webCLientCustomer()))
				.build();
		return factory.createClient(CustomerClient.class);
	}

	@Bean
	ProductClient productClient(){
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webCLientProduct()))
				.build();
		return factory.createClient(ProductClient.class);
	}

}
