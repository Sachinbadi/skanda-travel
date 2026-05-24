package com.skanda.travels.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateBookingRequest {

  @NotNull
  private Long tripId;

  /** Optional coupon such as SKANDA10 */
  private String couponCode;

  @NotEmpty
  @Valid
  private List<PassengerSeatRequest> passengers;

}
