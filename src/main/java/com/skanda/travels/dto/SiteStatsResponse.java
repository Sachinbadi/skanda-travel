package com.skanda.travels.dto;

public class SiteStatsResponse {

  private String happyCustomersLabel;
  private long busesInFleet;
  private long activeRoutes;
  private String onTimePerformanceLabel;

  public String getHappyCustomersLabel () {
    return happyCustomersLabel;
  }

  public void setHappyCustomersLabel (String happyCustomersLabel) {
    this.happyCustomersLabel = happyCustomersLabel;
  }

  public long getBusesInFleet () {
    return busesInFleet;
  }

  public void setBusesInFleet (long busesInFleet) {
    this.busesInFleet = busesInFleet;
  }

  public long getActiveRoutes () {
    return activeRoutes;
  }

  public void setActiveRoutes (long activeRoutes) {
    this.activeRoutes = activeRoutes;
  }

  public String getOnTimePerformanceLabel () {
    return onTimePerformanceLabel;
  }

  public void setOnTimePerformanceLabel (String onTimePerformanceLabel) {
    this.onTimePerformanceLabel = onTimePerformanceLabel;
  }
}
