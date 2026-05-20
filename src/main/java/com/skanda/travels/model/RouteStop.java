package com.skanda.travels.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "route_stops")
public class RouteStop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_route_id", nullable = false)
  private TravelRoute travelRoute;

  @Column(nullable = false)
  private String name;

  /** Order along the route (1-based) */
  @Column(nullable = false)
  private Integer sequenceOrder;

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

  public String getName () {
    return name;
  }

  public void setName (String name) {
    this.name = name;
  }

  public Integer getSequenceOrder () {
    return sequenceOrder;
  }

  public void setSequenceOrder (Integer sequenceOrder) {
    this.sequenceOrder = sequenceOrder;
  }
}
