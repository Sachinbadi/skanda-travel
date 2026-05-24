package com.skanda.travels.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skanda.travels.entity.Booking;

import org.springframework.data.jpa.repository.EntityGraph;

import com.skanda.travels.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  @EntityGraph(attributePaths = {"trip", "trip.travelRoute", "trip.travelRoute.stops", "trip.bus", "passengers"})
  List<Booking> findByUserIdOrderByBookingTimeDesc (Long userId);

  List<Booking> findByTripIdAndStatus (Long tripId, BookingStatus bookingStatus);

  @EntityGraph(attributePaths = {"trip", "trip.travelRoute", "trip.bus", "passengers"})
  Optional<Booking> findByBookingReference (String bookingReference);

  Optional<Booking> findByRazorpayOrderId(String razorpayOrderId);

  @EntityGraph(attributePaths = {"trip", "trip.travelRoute", "trip.travelRoute.stops", "trip.bus", "passengers"})
  Optional<Booking> findByBookingReferenceAndUserId (String bookingReference, Long userId);
}
