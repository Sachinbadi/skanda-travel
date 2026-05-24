package com.skanda.travels.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelRouteSummary {

  private Long id;
  private String origin;
  private String destination;
  private Integer distanceKm;
  private String estimatedDurationLabel;
  private List<String> viaStops = new ArrayList<>();
}
