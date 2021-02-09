package com.andrei.microservices.limitsservice.bean;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Limits {
    private int minimum;
    private int maximum;
}
