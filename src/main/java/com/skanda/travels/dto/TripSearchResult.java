package com.skanda.travels.dto;

import com.skanda.travels.model.BusType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TripSearchResult {

  private Long id;
  private String origin;
  private String destination;
  private Integer distanceKm;
  private String estimatedDurationLabel;
  private List<String> viaStops;
  private BusType busType;
  private String busNumber;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private BigDecimal baseFarePerPassenger;
  private int availableSeats;

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public String getOrigin () {
    return origin;
  }

  public void setOrigin (String origin) {
    this.origin = origin;
  }

  public String getDestination () {
    return destination;
  }

  public void setDestination (String destination) {
    this.destination = destination;
  }

  public Integer getDistanceKm () {
    return distanceKm;
  }

  public void setDistanceKm (Integer distanceKm) {
    this.distanceKm = distanceKm;
  }

  public String getEstimatedDurationLabel () {
    return estimatedDurationLabel;
  }

  public void setEstimatedDurationLabel (String estimatedDurationLabel) {
    this.estimatedDurationLabel = estimatedDurationLabel;
  }

  public List<String> getViaStops () {
    return viaStops;
  }

  public void setViaStops (List<String> viaStops) {
    this.viaStops = viaStops;
  }

  public BusType getBusType () {
    return busType;
  }

  public void setBusType (BusType busType) {
    this.busType = busType;
  }

  public String getBusNumber () {
    return busNumber;
  }

  public void setBusNumber (String busNumber) {
    this.busNumber = busNumber;
  }

  public LocalDateTime getDepartureTime () {
    return departureTime;
  }

  public void setDepartureTime (LocalDateTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalDateTime getArrivalTime () {
    return arrivalTime;
  }

  public void setArrivalTime (LocalDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public BigDecimal getBaseFarePerPassenger () {
    return baseFarePerPassenger;
  }

  public void setBaseFarePerPassenger (BigDecimal baseFarePerPassenger) {
    this.baseFarePerPassenger = baseFarePerPassenger;
  }

  public int getAvailableSeats () {
    return availableSeats;
  }

  public void setAvailableSeats (int availableSeats) {
    this.availableSeats = availableSeats;
  }
}
