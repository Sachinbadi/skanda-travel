package com.skanda.travels.web;

import com.skanda.travels.dto.BookingResponse;
import com.skanda.travels.dto.CreateBookingRequest;
import com.skanda.travels.security.AuthenticatedUserDetails;
import com.skanda.travels.service.BookingService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

  private final BookingService bookingService;

  public BookingController (BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookingResponse create (
      @AuthenticationPrincipal AuthenticatedUserDetails principal,
      @Valid @RequestBody CreateBookingRequest req
  ) {
    return bookingService.createBooking(principal.getUser(), req);
  }

  @GetMapping
  public List<BookingResponse> mine (@AuthenticationPrincipal AuthenticatedUserDetails principal) {
    return bookingService.listMyBookings(principal.getUser());
  }

  @GetMapping("/reference/{bookingReference}")
  public BookingResponse byReference (
      @AuthenticationPrincipal AuthenticatedUserDetails principal,
      @PathVariable String bookingReference
  ) {
    return bookingService.getBookingForUser(principal.getUser(), bookingReference);
  }
}
