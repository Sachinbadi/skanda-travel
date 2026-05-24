package com.skanda.travels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
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

}
