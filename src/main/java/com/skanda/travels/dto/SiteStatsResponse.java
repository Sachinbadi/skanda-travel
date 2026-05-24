package com.skanda.travels.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteStatsResponse {

  private String happyCustomersLabel;
  private long busesInFleet;
  private long activeRoutes;
  private String onTimePerformanceLabel;
}
