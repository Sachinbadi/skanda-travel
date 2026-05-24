package com.skanda.travels.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.skanda.travels.enums.BookingStatus;
@Getter 
@Setter
public class BookingResponse {

  private String bookingReference;
  private BookingStatus status;
  private Instant bookingTime;
  private BigDecimal totalFare;
  private String couponCodeApplied;
  private TripSearchResult trip;
  private List<PassengerView> passengers = new ArrayList<>();

  @Getter @Setter
  public static class PassengerView {

    private String fullName;
    private Integer seatNumber;

  }

}
