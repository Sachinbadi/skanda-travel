package com.skanda.travels.mapping;

import com.skanda.travels.dto.BookingResponse;
import com.skanda.travels.dto.TripSearchResult;
import com.skanda.travels.model.Booking;
import com.skanda.travels.model.Trip;
import com.skanda.travels.model.TravelRoute;
import java.util.Objects;
import java.util.stream.Collectors;

public final class TripMapper {

  private TripMapper () {}

  public static TripSearchResult toSearchResult (Trip trip) {
    TravelRoute route = Objects.requireNonNull(trip.getTravelRoute());
    TripSearchResult dto = new TripSearchResult();
    dto.setId(trip.getId());
    dto.setOrigin(route.getOrigin());
    dto.setDestination(route.getDestination());
    dto.setDistanceKm(route.getDistanceKm());
    dto.setEstimatedDurationLabel(route.getEstimatedDurationLabel());
    dto.setViaStops(
        route.getStops().stream()
            .sorted((a, b) -> Integer.compare(a.getSequenceOrder(), b.getSequenceOrder()))
            .map(s -> s.getName())
            .collect(Collectors.toList())
    );
    dto.setBusType(trip.getBus().getBusType());
    dto.setBusNumber(trip.getBus().getBusNumber());
    dto.setDepartureTime(trip.getDepartureTime());
    dto.setArrivalTime(trip.getArrivalTime());
    dto.setBaseFarePerPassenger(trip.getBaseFare());
    dto.setAvailableSeats(trip.getAvailableSeats());
    return dto;
  }

  public static BookingResponse toBookingResponse (Booking booking) {
    BookingResponse dto = new BookingResponse();
    dto.setBookingReference(booking.getBookingReference());
    dto.setStatus(booking.getStatus());
    dto.setBookingTime(booking.getBookingTime());
    dto.setTotalFare(booking.getTotalFare());
    dto.setCouponCodeApplied(booking.getCouponCodeApplied());
    dto.setTrip(toSearchResult(booking.getTrip()));
    dto.setPassengers(
        booking.getPassengers().stream().map(bp -> {
          BookingResponse.PassengerView v = new BookingResponse.PassengerView();
          v.setFullName(bp.getFullName());
          v.setSeatNumber(bp.getSeatNumber());
          return v;
        }).collect(Collectors.toList())
    );
    return dto;
  }
}
