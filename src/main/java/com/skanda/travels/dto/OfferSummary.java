package com.skanda.travels.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferSummary {

  private String couponCode;
  private BigDecimal discountPercent;
  private LocalDate validUntil;
  private String description;
}
