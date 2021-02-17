package com.andrei.microservices.currencyexchangeservice.controller;

import com.andrei.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.andrei.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeRepository repository;
    private final Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to ) {

        log.info("retrieveExchangeValue called with {} to {}", from, to);
        final CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to).orElseThrow();
        currencyExchange.setEnvironmentPort(environment.getProperty("local.server.port"));

        return currencyExchange;
    }
}
