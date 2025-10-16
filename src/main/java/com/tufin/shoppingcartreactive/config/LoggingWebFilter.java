package com.tufin.shoppingcartreactive.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Slf4j
@Configuration
@Order(-1)
public class LoggingWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long started = System.currentTimeMillis();
        ServerHttpRequest req = exchange.getRequest();
        String path = req.getURI().getPath();
        String method = req.getMethod().name();

        return exchange.getRequest().getBody()
                .collectList()
                .flatMap(dataBuffers -> {
                    if (log.isDebugEnabled() && "POST".equalsIgnoreCase(method) && path.equals("/graphql")) {
                        String body = dataBuffers.stream()
                                .map(buffer -> {
                                    byte[] bytes = new byte[buffer.readableByteCount()];
                                    buffer.read(bytes);
                                    return new String(bytes);
                                })
                                .collect(Collectors.joining());
                        log.debug("GraphQL request body: {}", body);
                    }
                    return chain.filter(exchange);
                })
                .doOnSubscribe(s -> log.info("Incoming request: {} {}", method, path))
                .doOnSuccess(v -> {
                    long took = System.currentTimeMillis() - started;
                    int status = exchange.getResponse().getStatusCode() != null
                            ? exchange.getResponse().getStatusCode().value()
                            : 200;
                    log.info("Completed: {} {} -> {} ({} ms)", method, path, status, took);
                })
                .doOnError(err -> {
                    long took = System.currentTimeMillis() - started;
                    log.error("Failed: {} {} ({} ms) - {}", method, path, took, err.toString());
                });
    }
}