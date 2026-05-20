package com.skanda.travels.dto;

import com.skanda.travels.model.BookingStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BookingResponse {

  private String bookingReference;
  private BookingStatus status;
  private Instant bookingTime;
  private BigDecimal totalFare;
  private String couponCodeApplied;
  private TripSearchResult trip;
  private List<PassengerView> passengers = new ArrayList<>();

  public static class PassengerView {

    private String fullName;
    private Integer seatNumber;

    public String getFullName () {
      return fullName;
    }

    public void setFullName (String fullName) {
      this.fullName = fullName;
    }

    public Integer getSeatNumber () {
      return seatNumber;
    }

    public void setSeatNumber (Integer seatNumber) {
      this.seatNumber = seatNumber;
    }
  }

  public String getBookingReference () {
    return bookingReference;
  }

  public void setBookingReference (String bookingReference) {
    this.bookingReference = bookingReference;
  }

  public BookingStatus getStatus () {
    return status;
  }

  public void setStatus (BookingStatus status) {
    this.status = status;
  }

  public Instant getBookingTime () {
    return bookingTime;
  }

  public void setBookingTime (Instant bookingTime) {
    this.bookingTime = bookingTime;
  }

  public BigDecimal getTotalFare () {
    return totalFare;
  }

  public void setTotalFare (BigDecimal totalFare) {
    this.totalFare = totalFare;
  }

  public String getCouponCodeApplied () {
    return couponCodeApplied;
  }

  public void setCouponCodeApplied (String couponCodeApplied) {
    this.couponCodeApplied = couponCodeApplied;
  }

  public TripSearchResult getTrip () {
    return trip;
  }

  public void setTrip (TripSearchResult trip) {
    this.trip = trip;
  }

  public List<PassengerView> getPassengers () {
    return passengers;
  }

  public void setPassengers (List<PassengerView> passengers) {
    this.passengers = passengers;
  }
}
