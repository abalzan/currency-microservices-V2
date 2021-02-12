package com.andrei.microservices.currencyexchangeservice.controller;

import com.andrei.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.andrei.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeRepository repository;
    private final Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to ) {

        final CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to).orElseThrow();
        currencyExchange.setEnvironmentPort(environment.getProperty("local.server.port"));

        return currencyExchange;
    }
}
