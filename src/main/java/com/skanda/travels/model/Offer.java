package com.skanda.travels.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
public class Offer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String couponCode;

  @Column(nullable = false)
  private BigDecimal discountPercent;

  private LocalDate validUntil;

  @Column(nullable = false)
  private Boolean active = true;

  private String description;

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

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

  public Boolean getActive () {
    return active;
  }

  public void setActive (Boolean active) {
    this.active = active;
  }

  public String getDescription () {
    return description;
  }

  public void setDescription (String description) {
    this.description = description;
  }
}
