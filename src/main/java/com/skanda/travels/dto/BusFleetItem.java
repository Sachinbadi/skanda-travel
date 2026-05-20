package com.skanda.travels.dto;

import com.skanda.travels.model.BusType;

public class BusFleetItem {

  private Long id;
  private String busNumber;
  private BusType busType;
  private Integer totalSeats;
  private String seatingConfig;
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
