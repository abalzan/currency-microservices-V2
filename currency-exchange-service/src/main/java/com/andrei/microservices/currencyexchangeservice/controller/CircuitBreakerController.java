package com.andrei.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class CircuitBreakerController {

    @GetMapping("/sample-api")
    @Retry(name="sample-api", fallbackMethod = "hardCodedResponse")
    public String sampleApi() {
        log.info("sample-api call received!");
        //add dummy call to force the error!
        final ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);
        return forEntity.getBody();
    }

    public String hardCodedResponse(Exception ex) {
        return "Fallback response";
    }
}
