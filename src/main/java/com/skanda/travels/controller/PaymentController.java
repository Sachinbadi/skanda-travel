package com.skanda.travels.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import com.skanda.travels.dto.BookingResponse;
import com.skanda.travels.dto.PaymentVerificationRequest;
import com.skanda.travels.dto.RazorpayResponse;
import com.skanda.travels.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    public RazorpayResponse createOrder(@RequestParam String bookingReference) {
        return paymentService.createOrder(bookingReference);
    }

    @PostMapping("/verify")
    public BookingResponse verifyOrder(@Valid @RequestBody PaymentVerificationRequest req) {
        return paymentService.verifyAndConfirm(req);
    }

}
