package com.skanda.travels.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RazorpayResponse {

    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String keyId;
}
