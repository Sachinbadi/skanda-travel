package com.skanda.travels.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@ConfigurationProperties(prefix = "razorpay")
public class RazorpayProperties {

    private String keyId;
    private String keySecret;
    
}
