package com.skanda.travels.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerSeatRequest {

  @NotBlank
  private String fullName;

  private Integer seatNumber;
}
