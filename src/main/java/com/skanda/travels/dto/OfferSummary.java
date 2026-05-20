package com.skanda.travels.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OfferSummary {

  private String couponCode;
  private BigDecimal discountPercent;
  private LocalDate validUntil;
  private String description;

  public String getCouponCode () {
    return couponCode;
  }

  public void setCouponCode (String couponCode) {
    this.couponCode = couponCode;
  }

  public BigDecimal getDiscountPercent () {
    return discountPercent;
  }

  public void setDiscountPercent (BigDecimal discountPercent) {
    this.discountPercent = discountPercent;
  }

  public LocalDate getValidUntil () {
    return validUntil;
  }

  public void setValidUntil (LocalDate validUntil) {
    this.validUntil = validUntil;
  }

  public String getDescription () {
    return description;
  }

  public void setDescription (String description) {
    this.description = description;
  }
}
