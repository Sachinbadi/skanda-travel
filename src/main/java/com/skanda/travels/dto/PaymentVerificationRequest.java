package com.skanda.travels.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentVerificationRequest {

    @NotBlank
    private String razorpayPaymentId;
    
    @NotBlank
    private String razorpayOrderId;

    @NotBlank
    private String razorpaySignature;

    @NotBlank
    private String bookingReference;

}
