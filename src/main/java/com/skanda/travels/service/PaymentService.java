package com.skanda.travels.service;

import java.math.BigDecimal;

import com.skanda.travels.exception.BadRequestException;
import com.skanda.travels.exception.NotFoundException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.skanda.travels.config.RazorpayProperties;
import com.skanda.travels.enums.BookingStatus;
import com.skanda.travels.dto.BookingResponse;
import com.skanda.travels.dto.PaymentVerificationRequest;
import com.skanda.travels.dto.RazorpayResponse;
import com.skanda.travels.entity.Booking;
import com.skanda.travels.repository.BookingRepository;

@Service
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final RazorpayProperties razorpayProperties;

    public PaymentService(
            BookingRepository bookingRepository,
            BookingService bookingService,
            RazorpayProperties razorpayProperties) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.razorpayProperties = razorpayProperties;
    }

    @Transactional
    public RazorpayResponse createOrder(String bookingReference) {

        Order order;
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new NotFoundException("Booking reference not found"));

        if (!booking.getStatus().equals(BookingStatus.PENDING)) {
            throw new BadRequestException("...");
        }
        try {
            RazorpayClient client = new RazorpayClient(razorpayProperties.getKeyId(),
                    razorpayProperties.getKeySecret());
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", booking.getTotalFare().multiply(BigDecimal.valueOf(100)).intValue()); // Razorpay
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", booking.getBookingReference());
            order = client.orders.create(orderRequest);
            booking.setRazorpayOrderId(order.get("id"));
        } catch (RazorpayException e) {
            throw new BadRequestException("Failed to create payment order: " + e.getMessage());
        }
        bookingRepository.save(booking);
        return buildResponse(order,booking);

    }

    public BookingResponse verifyAndConfirm(PaymentVerificationRequest req) {
        // Step 1 — reconstruct the signature using orderId + paymentId + your secret
        String payload = req.getRazorpayOrderId() + "|" + req.getRazorpayPaymentId();
        try {
            String generated = Utils.getHash(payload, razorpayProperties.getKeySecret());
            // Step 2 — compare; if mismatch, the payment is fake
            if (!generated.equals(req.getRazorpaySignature())) {
                throw new BadRequestException("Invalid payment signature");
            }
        } catch (RazorpayException e) {
            throw new BadRequestException("Signature verification failed: " + e.getMessage());
        }

        // Step 3 — find the booking tied to this Razorpay order
        Booking booking = bookingRepository.findByRazorpayOrderId(req.getRazorpayOrderId())
                .orElseThrow(() -> new NotFoundException("No booking found for this order"));

        // Step 4 — confirm it (PENDING → CONFIRMED)
        return bookingService.confirmBooking(booking.getBookingReference());
    }

    private RazorpayResponse buildResponse(Order order, Booking booking) {
        RazorpayResponse response = new RazorpayResponse();
        response.setOrderId(order.get("id"));
        response.setAmount(booking.getTotalFare());
        response.setCurrency("INR");
        response.setKeyId(razorpayProperties.getKeyId());
        return response;
    }

}
