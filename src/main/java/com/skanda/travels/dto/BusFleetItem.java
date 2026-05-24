package com.skanda.travels.dto;

import com.skanda.travels.enums.BusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusFleetItem {

  private Long id;
  private String busNumber;
  private BusType busType;
  private Integer totalSeats;
  private String seatingConfig;
  private String amenities;
}
