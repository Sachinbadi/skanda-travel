package com.skanda.travels.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "travel_route_id", nullable = false)
  private TravelRoute travelRoute;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bus_id", nullable = false)
  private Bus bus;

  @Column(nullable = false)
  private LocalDateTime departureTime;

  private LocalDateTime arrivalTime;

  @Column(nullable = false)
  private BigDecimal baseFare;

  @Column(nullable = false)
  private Integer availableSeats;

  @OneToMany(mappedBy = "trip")
  private List<Booking> bookings = new ArrayList<>();

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public TravelRoute getTravelRoute () {
    return travelRoute;
  }

  public void setTravelRoute (TravelRoute travelRoute) {
    this.travelRoute = travelRoute;
  }

  public Bus getBus () {
    return bus;
  }

  public void setBus (Bus bus) {
    this.bus = bus;
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

  public BigDecimal getBaseFare () {
    return baseFare;
  }

  public void setBaseFare (BigDecimal baseFare) {
    this.baseFare = baseFare;
  }

  public Integer getAvailableSeats () {
    return availableSeats;
  }

  public void setAvailableSeats (Integer availableSeats) {
    this.availableSeats = availableSeats;
  }

  public List<Booking> getBookings () {
    return bookings;
  }

  public void setBookings (List<Booking> bookings) {
    this.bookings = bookings;
  }
}
