package com.skanda.travels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Getter @Setter
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

}
