package com.skanda.travels.bootstrap;

import com.skanda.travels.model.Bus;
import com.skanda.travels.model.BusType;
import com.skanda.travels.model.Offer;
import com.skanda.travels.model.Role;
import com.skanda.travels.model.RouteStop;
import com.skanda.travels.model.TravelRoute;
import com.skanda.travels.model.Trip;
import com.skanda.travels.model.User;
import com.skanda.travels.repository.BusRepository;
import com.skanda.travels.repository.OfferRepository;
import com.skanda.travels.repository.TripRepository;
import com.skanda.travels.repository.TravelRouteRepository;
import com.skanda.travels.repository.UserRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DemoDataInitializer implements ApplicationRunner {

  private final BusRepository busRepository;
  private final TravelRouteRepository travelRouteRepository;
  private final TripRepository tripRepository;
  private final OfferRepository offerRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public DemoDataInitializer (
      BusRepository busRepository,
      TravelRouteRepository travelRouteRepository,
      TripRepository tripRepository,
      OfferRepository offerRepository,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder
  ) {
    this.busRepository = busRepository;
    this.travelRouteRepository = travelRouteRepository;
    this.tripRepository = tripRepository;
    this.offerRepository = offerRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  @Override
  public void run (ApplicationArguments args) {
    if (travelRouteRepository.count() > 0) {
      return;
    }

    Bus sleeper = persistBus(
        "SKD-9021",
        BusType.AC_SLEEPER,
        30,
        "2 + 1",
        "Charging point · Reading light · Pillow & blanket · Water bottle · Wi-Fi"
    );

    persistBus(
        "SKD-1188",
        BusType.AC_SEATER,
        42,
        "2 + 2",
        "Charging point · AC vents · Recliner seats"
    );

    TravelRoute jindalMangalore = new TravelRoute();
    jindalMangalore.setOrigin("Jindal");
    jindalMangalore.setDestination("Mangalore");
    jindalMangalore.setDistanceKm(640);
    jindalMangalore.setEstimatedDurationLabel("11–12 hrs");
    addOrderedStop(jindalMangalore, "Hospet", 1);
    addOrderedStop(jindalMangalore, "Hubli", 2);
    addOrderedStop(jindalMangalore, "Ankola", 3);

    TravelRoute mangaloreHubli = new TravelRoute();
    mangaloreHubli.setOrigin("Mangalore");
    mangaloreHubli.setDestination("Hubli");
    mangaloreHubli.setDistanceKm(280);
    mangaloreHubli.setEstimatedDurationLabel("5–6 hrs");
    travelRouteRepository.save(mangaloreHubli);

    jindalMangalore = travelRouteRepository.save(jindalMangalore);

    seedTripsAlongRoute(jindalMangalore, sleeper, LocalDate.now(), 21, BigDecimal.valueOf(899));

    Offer offer = new Offer();
    offer.setCouponCode("SKANDA10");
    offer.setDiscountPercent(BigDecimal.valueOf(10));
    offer.setActive(true);
    offer.setDescription("Flat 10% OFF on online bookings.");
    offer.setValidUntil(LocalDate.now().plusYears(1));
    offerRepository.save(offer);

    createUserIfAbsent(
        "demo",
        "demo@skandatravels.test",
        passwordEncoder.encode("Travel123"),
        Set.of(Role.ROLE_USER),
        "9000000000"
    );
    createUserIfAbsent(
        "admin",
        "admin@skandatravels.test",
        passwordEncoder.encode("Admin123"),
        Set.of(Role.ROLE_ADMIN, Role.ROLE_USER),
        null
    );
  }

  private void createUserIfAbsent (
      String username,
      String email,
      String encodedPassword,
      Set<Role> roles,
      String phone
  ) {
    if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
      return;
    }
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(encodedPassword);
    user.setPhone(phone);
    user.getRoles().addAll(roles);
    userRepository.save(user);
  }

  private Bus persistBus (
      String number,
      BusType busType,
      int totalSeats,
      String seating,
      String amenities
  ) {
    Bus bus = new Bus();
    bus.setBusNumber(number);
    bus.setBusType(busType);
    bus.setTotalSeats(totalSeats);
    bus.setSeatingConfig(seating);
    bus.setAmenities(amenities);
    return busRepository.save(bus);
  }

  private void addOrderedStop (TravelRoute route, String stopName, int order) {
    RouteStop rs = new RouteStop();
    rs.setTravelRoute(route);
    rs.setName(stopName);
    rs.setSequenceOrder(order);
    route.getStops().add(rs);
  }

  private void seedTripsAlongRoute (TravelRoute route, Bus bus, LocalDate startInclusive, int hour, BigDecimal fare) {
    LocalDate horizon = startInclusive.plusDays(21);
    for (LocalDate d = startInclusive; !d.isAfter(horizon); d = d.plusDays(1)) {
      LocalDateTime departure = d.atTime(hour, 0);
      LocalDateTime arrival = departure.plusHours(12);
      Trip trip = new Trip();
      trip.setTravelRoute(route);
      trip.setBus(bus);
      trip.setDepartureTime(departure);
      trip.setArrivalTime(arrival);
      trip.setBaseFare(fare);
      trip.setAvailableSeats(bus.getTotalSeats());
      tripRepository.save(trip);
    }
  }
}
