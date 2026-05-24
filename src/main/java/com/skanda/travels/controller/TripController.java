package com.skanda.travels.controller;

import com.skanda.travels.dto.TripSearchResult;
import com.skanda.travels.service.CatalogService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {

  private final CatalogService catalogService;

  public TripController (CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  /**
   * Search scheduled departures matching origin / destination / local journey date (server timezone).
   */
  @GetMapping("/search")
  public List<TripSearchResult> search (
      @RequestParam(name = "from") String from,
      @RequestParam(name = "to") String to,
      @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      @RequestParam(name = "passengers", defaultValue = "1") int passengers
  ) {
    return catalogService.searchTrips(from, to, date, passengers);
  }

  @GetMapping("/{id}")
  public TripSearchResult detail (@PathVariable Long id) {
    return catalogService.getTrip(id);
  }
}
