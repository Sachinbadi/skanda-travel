package com.skanda.travels.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateBookingRequest {

  @NotNull
  private Long tripId;

  /** Optional coupon such as SKANDA10 */
  private String couponCode;

  @NotEmpty
  @Valid
  private List<PassengerSeatRequest> passengers;

  public Long getTripId () {
    return tripId;
  }

  public void setTripId (Long tripId) {
    this.tripId = tripId;
  }

  public String getCouponCode () {
    return couponCode;
  }

  public void setCouponCode (String couponCode) {
    this.couponCode = couponCode;
  }

  public List<PassengerSeatRequest> getPassengers () {
    return passengers;
  }

  public void setPassengers (List<PassengerSeatRequest> passengers) {
    this.passengers = passengers;
  }
}
