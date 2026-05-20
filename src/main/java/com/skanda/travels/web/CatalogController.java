package com.skanda.travels.web;

import com.skanda.travels.dto.BusFleetItem;
import com.skanda.travels.dto.OfferSummary;
import com.skanda.travels.dto.SiteStatsResponse;
import com.skanda.travels.dto.TravelRouteSummary;
import com.skanda.travels.service.CatalogService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CatalogController {

  private final CatalogService catalogService;

  public CatalogController (CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  @GetMapping("/routes")
  public List<TravelRouteSummary> routes () {
    return catalogService.listRoutes();
  }

  @GetMapping("/buses")
  public List<BusFleetItem> fleet () {
    return catalogService.listFleet();
  }

  @GetMapping("/offers")
  public List<OfferSummary> offers () {
    return catalogService.activeOffers();
  }

  @GetMapping("/stats")
  public SiteStatsResponse stats () {
    return catalogService.snapshotStats();
  }
}
