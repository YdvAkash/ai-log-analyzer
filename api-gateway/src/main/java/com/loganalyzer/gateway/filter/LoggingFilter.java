package com.loganalyzer.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. Pre-filter: Request aane par log karein
        long startTime = System.currentTimeMillis();
        String path = exchange.getRequest().getPath().toString();
        String method = exchange.getRequest().getMethod().toString();

        log.info("Incoming Request: {} {} | Source: {}", method, path, 
                 exchange.getRequest().getRemoteAddress());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 2. Post-filter: Response jaane par processing time log karein
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            log.info("Outgoing Response for {}: Status Code {} | Time Taken: {}ms", 
                     path, exchange.getResponse().getStatusCode(), duration);
        }));
    }

    @Override
    public int getOrder() {
        // Sabse pehle execute ho isliye lowest value
        return Ordered.HIGHEST_PRECEDENCE;
    }
}