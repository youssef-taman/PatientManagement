package com.example.apigateway.filter;

import com.example.apigateway.entity.UserDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private final WebClient webClient;

    public JwtAuthenticationGatewayFilterFactory(WebClient.Builder builder , @Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = builder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.error("Missing or invalid Authorization header");
                return onError(exchange);
            }

            String token = authHeader.substring(7);

            return webClient.get()
                    .uri("/api/v1/auth/validate")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .flatMap(userDTO -> {
                        exchange.getRequest().mutate()
                                .header("X-User-Email", userDTO.getEmail())
                                .header("X-User-Role", userDTO.getRole())
                                .build();
                        return chain.filter(exchange);
                    })
                    .onErrorResume(e -> {
                        log.error("Token validation failed: {}", e.getMessage());
                        return onError(exchange);
                    });
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


}
