package com.skanda.travels.dto;

import java.util.ArrayList;
import java.util.List;

public class TravelRouteSummary {

  private Long id;
  private String origin;
  private String destination;
  private Integer distanceKm;
  private String estimatedDurationLabel;
  private List<String> viaStops = new ArrayList<>();

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
}
