package com.skanda.travels.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_routes")
public class TravelRoute {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String origin;

  @Column(nullable = false)
  private String destination;

  /** Distance in kilometers */
  @Column(nullable = false)
  private Integer distanceKm;

  /** Human-readable duration, e.g. "11–12 hrs" */
  private String estimatedDurationLabel;

  @OneToMany(mappedBy = "travelRoute", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("sequenceOrder ASC")
  private List<RouteStop> stops = new ArrayList<>();

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

  public List<RouteStop> getStops () {
    return stops;
  }

  public void setStops (List<RouteStop> stops) {
    this.stops = stops;
  }
}
