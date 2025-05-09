package com.userservice.circuitbreaker;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

    public <T> ResponseEntity<T> callService(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("serviceCircuitBreaker");

        return circuitBreaker.run(
                () -> restTemplate.exchange(url, method, requestEntity, responseType),
                throwable -> new ResponseEntity<>(handleError(throwable), HttpStatus.SERVICE_UNAVAILABLE)
        );
    }

    private <T> T handleError(Throwable throwable) {
        throw new RuntimeException("Fallback response due to: " + throwable.getMessage());
    }
}
