package com.andrei.microservices.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(predicateSpec -> predicateSpec.path("/get")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("myParam", "myValueParam"))
                        .uri("http://httpbin.org:80"))
                //routing the requests to an specific path
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                //routing different request to a existent endpoint
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("currency-conversion-new/(?<segment>.*)", "currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
