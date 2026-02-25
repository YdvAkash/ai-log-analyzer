package com.loganalyzer.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 1. Ingestion Service Route
                .route("ingestion-route", r -> r.path("/api/logs/**")
                        .uri("http://localhost:8081"))

                // 2. Parsing/Query Service Route (For fetching logs from DB/Elastic)
                .route("parsing-route", r -> r.path("/api/query/**")
                        .uri("http://localhost:8082"))

                // 3. AI Analysis Service Route (For fetching AI insights)
                .route("ai-route", r -> r.path("/api/analysis/**")
                        .uri("http://localhost:8084"))

                // 4. Alert Service Route
                .route("alert-route", r -> r.path("/api/alerts/**")
                        .uri("http://localhost:8085"))
                
                .build();
    }
}