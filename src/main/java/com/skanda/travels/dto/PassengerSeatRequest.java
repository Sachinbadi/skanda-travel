package com.skanda.travels.dto;

import jakarta.validation.constraints.NotBlank;

public class PassengerSeatRequest {

  @NotBlank
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
