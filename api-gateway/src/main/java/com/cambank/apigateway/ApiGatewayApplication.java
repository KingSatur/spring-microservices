package com.cambank.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.instrument.web.TraceWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(p -> {
					return p.path("/cambank/accounts/**").filters(f -> {
						return f.rewritePath("/cambank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString());
					}).uri("lb://ACCOUNTS");
				})
				.route(p -> {
					return p.path("/cambank/loans/**").filters(f -> {
						return f.rewritePath("/cambank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString());
					}).uri("lb://LOANS");
				})
				.route(p -> {
					return p.path("/cambank/cards/**").filters(f -> {
						return f.rewritePath("/cambank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString());
					}).uri("lb://CARDS");
				}).build();
	}

}
