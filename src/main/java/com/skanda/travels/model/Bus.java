package com.skanda.travels.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
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

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public String getBusNumber () {
    return busNumber;
  }

  public void setBusNumber (String busNumber) {
    this.busNumber = busNumber;
  }

  public BusType getBusType () {
    return busType;
  }

  public void setBusType (BusType busType) {
    this.busType = busType;
  }

  public Integer getTotalSeats () {
    return totalSeats;
  }

  public void setTotalSeats (Integer totalSeats) {
    this.totalSeats = totalSeats;
  }

  public String getSeatingConfig () {
    return seatingConfig;
  }

  public void setSeatingConfig (String seatingConfig) {
    this.seatingConfig = seatingConfig;
  }

  public String getAmenities () {
    return amenities;
  }

  public void setAmenities (String amenities) {
    this.amenities = amenities;
  }
}
