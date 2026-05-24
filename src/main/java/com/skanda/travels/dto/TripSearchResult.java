package com.skanda.travels.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.skanda.travels.enums.BusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
