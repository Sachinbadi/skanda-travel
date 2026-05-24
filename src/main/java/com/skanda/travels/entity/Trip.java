package com.skanda.travels.entity;

import jakarta.annotation.Generated;
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

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
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

}
