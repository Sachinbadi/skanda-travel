package com.skanda.travels.entity;

import com.skanda.travels.enums.BusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "buses")
public class Bus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String busNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BusType busType;

  @Column(nullable = false)
  private Integer totalSeats;

  private String seatingConfig;

  /** Comma-separated or free-text amenities */
  private String amenities;

}
