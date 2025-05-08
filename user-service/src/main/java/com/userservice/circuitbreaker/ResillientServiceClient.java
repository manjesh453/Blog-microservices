package com.userservice.circuitbreaker;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResillientServiceClient {
    private final RestTemplate restTemplate;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public ResillientServiceClient(RestTemplate restTemplate, CircuitBreakerFactory circuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public Object callService(String url, HttpMethod method, HttpEntity<Object> requestEntity) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("serviceCircuitBreaker");

        return circuitBreaker.run(
                () -> {
                    ResponseEntity<Object> response = restTemplate.exchange(url, method, requestEntity, Object.class);
                    return response;
                },
                throwable -> handleError(throwable)
        );
    }

    private String handleError(Throwable throwable) {
        return "Fallback response due to: " + throwable.getMessage();
    }
}
