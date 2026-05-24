package com.skanda.travels.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
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

}
