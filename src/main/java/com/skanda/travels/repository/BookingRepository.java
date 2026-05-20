package com.skanda.travels.repository;

import com.skanda.travels.model.Booking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  @EntityGraph(attributePaths = {"trip", "trip.travelRoute", "trip.travelRoute.stops", "trip.bus", "passengers"})
  List<Booking> findByUserIdOrderByBookingTimeDesc (Long userId);

  @EntityGraph(attributePaths = {"trip", "trip.travelRoute", "trip.bus", "passengers"})
  Optional<Booking> findByBookingReference (String bookingReference);

  @EntityGraph(attributePaths = {"trip", "trip.travelRoute", "trip.travelRoute.stops", "trip.bus", "passengers"})
  Optional<Booking> findByBookingReferenceAndUserId (String bookingReference, Long userId);
}
