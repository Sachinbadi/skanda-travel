package com.skanda.travels.service;

import com.skanda.travels.dto.BusFleetItem;
import com.skanda.travels.dto.OfferSummary;
import com.skanda.travels.dto.SiteStatsResponse;
import com.skanda.travels.dto.TravelRouteSummary;
import com.skanda.travels.dto.TripSearchResult;
import com.skanda.travels.entity.Bus;
import com.skanda.travels.entity.Offer;
import com.skanda.travels.entity.RouteStop;
import com.skanda.travels.entity.TravelRoute;
import com.skanda.travels.exception.NotFoundException;
import com.skanda.travels.mapping.TripMapper;
import com.skanda.travels.repository.BookingRepository;
import com.skanda.travels.repository.BusRepository;
import com.skanda.travels.repository.OfferRepository;
import com.skanda.travels.repository.TripRepository;
import com.skanda.travels.repository.TravelRouteRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogService {

  private final TripRepository tripRepository;
  private final TravelRouteRepository travelRouteRepository;
  private final BusRepository busRepository;
  private final OfferRepository offerRepository;
  private final BookingRepository bookingRepository;

  public CatalogService (
      TripRepository tripRepository,
      TravelRouteRepository travelRouteRepository,
      BusRepository busRepository,
      OfferRepository offerRepository,
      BookingRepository bookingRepository
  ) {
    this.tripRepository = tripRepository;
    this.travelRouteRepository = travelRouteRepository;
    this.busRepository = busRepository;
    this.offerRepository = offerRepository;
    this.bookingRepository = bookingRepository;
  }

  @Transactional(readOnly = true)
  public List<TripSearchResult> searchTrips (String from, String to, LocalDate journeyDate, int passengers) {
    if (from == null || from.isBlank() || to == null || to.isBlank() || journeyDate == null) {
      return List.of();
    }

    LocalDateTime start = journeyDate.atStartOfDay();
    LocalDateTime endExclusive = journeyDate.plusDays(1).atStartOfDay();
    int pax = Math.max(1, passengers);

    return tripRepository.searchTrips(
        from.trim(),
        to.trim(),
        start,
        endExclusive,
        pax
    ).stream()
        .map(TripMapper::toSearchResult)
        .toList();
  }

  @Transactional(readOnly = true)
  public TripSearchResult getTrip (Long tripId) {
    return tripRepository.findById(tripId)
        .map(TripMapper::toSearchResult)
        .orElseThrow(() -> new NotFoundException("Trip not available"));
  }

  @Transactional(readOnly = true)
  public List<TravelRouteSummary> listRoutes () {
    return travelRouteRepository.findAll().stream()
        .map(this::toRouteSummary)
        .sorted(Comparator.comparing(r -> r.getOrigin().toLowerCase(Locale.ROOT)))
        .toList();
  }

  @Transactional(readOnly = true)
  public List<BusFleetItem> listFleet () {
    return busRepository.findAll().stream()
        .map(this::toBusItem)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<OfferSummary> activeOffers () {
    return offerRepository.findCurrentlyActiveOffers(LocalDate.now()).stream()
        .map(this::toOfferSummary)
        .toList();
  }

  @Transactional(readOnly = true)
  public SiteStatsResponse snapshotStats () {
    long fleet = busRepository.count();
    long routes = travelRouteRepository.count();
    long tickets = bookingRepository.count();

    SiteStatsResponse dto = new SiteStatsResponse();
    dto.setHappyCustomersLabel(tickets >= 1000 ? (tickets / 1000) + "K+ Happy Customers" : tickets + "+ Happy Customers");
    dto.setBusesInFleet(fleet);
    dto.setActiveRoutes(routes);
    dto.setOnTimePerformanceLabel("99% On-Time Performance");
    return dto;
  }

  private TravelRouteSummary toRouteSummary (TravelRoute route) {
    TravelRouteSummary dto = new TravelRouteSummary();
    dto.setId(route.getId());
    dto.setOrigin(route.getOrigin());
    dto.setDestination(route.getDestination());
    dto.setDistanceKm(route.getDistanceKm());
    dto.setEstimatedDurationLabel(route.getEstimatedDurationLabel());
    dto.setViaStops(
        route.getStops().stream()
            .sorted(Comparator.comparingInt(RouteStop::getSequenceOrder))
            .map(RouteStop::getName)
            .collect(Collectors.toList())
    );
    return dto;
  }

  private BusFleetItem toBusItem (Bus bus) {
    BusFleetItem dto = new BusFleetItem();
    dto.setId(bus.getId());
    dto.setBusNumber(bus.getBusNumber());
    dto.setBusType(bus.getBusType());
    dto.setTotalSeats(bus.getTotalSeats());
    dto.setSeatingConfig(bus.getSeatingConfig());
    dto.setAmenities(bus.getAmenities());
    return dto;
  }

  private OfferSummary toOfferSummary (Offer offer) {
    OfferSummary dto = new OfferSummary();
    dto.setCouponCode(offer.getCouponCode());
    dto.setDiscountPercent(offer.getDiscountPercent());
    dto.setValidUntil(offer.getValidUntil());
    dto.setDescription(offer.getDescription());
    return dto;
  }
}
