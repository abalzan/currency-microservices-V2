package com.andrei.microservices.limitsservice.controller;

import com.andrei.microservices.limitsservice.bean.Limits;
import com.andrei.microservices.limitsservice.config.LimitsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LimitsController {

    private final LimitsConfig limitsConfig;

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return Limits.builder()
                .minimum(limitsConfig.getMinimum())
                .maximum(limitsConfig.getMaximum())
                .build();
    }
}
