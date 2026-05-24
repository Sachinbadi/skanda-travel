package com.skanda.travels.service;

import com.skanda.travels.dto.BookingResponse;
import com.skanda.travels.dto.CreateBookingRequest;
import com.skanda.travels.dto.PassengerSeatRequest;
import com.skanda.travels.entity.Booking;
import com.skanda.travels.entity.BookingPassenger;
import com.skanda.travels.entity.Offer;
import com.skanda.travels.entity.Trip;
import com.skanda.travels.entity.User;
import com.skanda.travels.enums.BookingStatus;
import com.skanda.travels.enums.Role;
import com.skanda.travels.exception.BadRequestException;
import com.skanda.travels.exception.NotFoundException;
import com.skanda.travels.mapping.TripMapper;
import com.skanda.travels.repository.BookingRepository;
import com.skanda.travels.repository.OfferRepository;
import com.skanda.travels.repository.TripRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;
  private final TripRepository tripRepository;
  private final OfferRepository offerRepository;

  public BookingService(
      BookingRepository bookingRepository,
      TripRepository tripRepository,
      OfferRepository offerRepository) {
    this.bookingRepository = bookingRepository;
    this.tripRepository = tripRepository;
    this.offerRepository = offerRepository;
  }

  @Transactional
  public BookingResponse createBooking(User user, CreateBookingRequest req) {
    Trip trip = tripRepository.findLockedById(req.getTripId())
        .orElseThrow(() -> new NotFoundException("Trip not found"));

    List<PassengerSeatRequest> passengerLines = req.getPassengers();
    if (passengerLines.isEmpty()) {
      throw new BadRequestException("At least one passenger is required");
    }

    int seatsNeeded = passengerLines.size();
    if (trip.getAvailableSeats() < seatsNeeded) {
      throw new BadRequestException("Not enough seats remaining for this trip");
    }

    Offer offerApplied = resolveOffer(req.getCouponCode());

    BigDecimal subtotal = trip.getBaseFare().multiply(BigDecimal.valueOf(seatsNeeded));
    BigDecimal discount = BigDecimal.ZERO;
    String couponStored = null;
    if (offerApplied != null) {
      discount = subtotal
          .multiply(offerApplied.getDiscountPercent())
          .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
      couponStored = offerApplied.getCouponCode();
    }

    BigDecimal total = subtotal.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    if (total.compareTo(BigDecimal.ZERO) < 0) {
      total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    trip.setAvailableSeats(trip.getAvailableSeats() - seatsNeeded);
    tripRepository.save(trip);

    Booking booking = new Booking();
    booking.setUser(user);
    booking.setTrip(trip);
    booking.setNumberOfPassengers(seatsNeeded);
    booking.setTotalFare(total);
    booking.setCouponCodeApplied(couponStored);
    booking.setStatus(BookingStatus.PENDING);

    List<BookingPassenger> persistedPassengers = new ArrayList<>();
    for (PassengerSeatRequest line : passengerLines) {
      BookingPassenger bp = new BookingPassenger();
      bp.setBooking(booking);
      bp.setFullName(line.getFullName().trim());
      bp.setSeatNumber(line.getSeatNumber());
      persistedPassengers.add(bp);
    }
    booking.setPassengers(persistedPassengers);

    booking = bookingRepository.save(booking);
    return TripMapper.toBookingResponse(booking);
  }

  private Offer resolveOffer(String rawCode) {
    if (rawCode == null || rawCode.isBlank()) {
      return null;
    }
    String code = rawCode.trim().toUpperCase(Locale.ROOT);
    Offer offer = offerRepository.findByCouponCodeIgnoreCase(code)
        .orElseThrow(() -> new BadRequestException("Unknown or inactive coupon: " + code));

    if (!Boolean.TRUE.equals(offer.getActive())) {
      throw new BadRequestException("Coupon is not active");
    }
    LocalDate today = LocalDate.now();
    if (offer.getValidUntil() != null && offer.getValidUntil().isBefore(today)) {
      throw new BadRequestException("Coupon has expired");
    }
    return offer;
  }

  @Transactional(readOnly = true)
  public List<BookingResponse> listMyBookings(User user) {
    return bookingRepository.findByUserIdOrderByBookingTimeDesc(user.getId()).stream()
        .map(TripMapper::toBookingResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  public BookingResponse getBookingForUser(User user, String reference) {
    Booking booking = bookingRepository.findByBookingReferenceAndUserId(reference.trim(), user.getId())
        .orElseThrow(() -> new NotFoundException("Booking not found"));
    return TripMapper.toBookingResponse(booking);
  }

  @Transactional
  public BookingResponse confirmBooking(String bookingReference) {
    Booking booking = bookingRepository.findByBookingReference(bookingReference.trim())
        .orElseThrow(() -> new NotFoundException("Booking reference not found"));
    if (!booking.getStatus().equals(BookingStatus.PENDING)) {
      throw new BadRequestException("Booking is either cancelled or already confirmed!");
    }
    booking.setStatus(BookingStatus.CONFIRMED);
    bookingRepository.save(booking);
    return TripMapper.toBookingResponse(booking);
  }

  @Transactional
  public BookingResponse cancelBooking(User user, String bookingReference) {
    Booking booking = bookingRepository.findByBookingReferenceAndUserId(bookingReference.trim(), user.getId())
        .orElseThrow(() -> new NotFoundException("Booking reference not found"));

    if (!booking.getStatus().equals(BookingStatus.CONFIRMED)) {
      throw new BadRequestException("Only confirmed bookings can be cancelled");
    }
    booking.setStatus(BookingStatus.REFUND_INITIATED);
    booking.getTrip().setAvailableSeats(booking.getTrip().getAvailableSeats() + booking.getNumberOfPassengers());
    tripRepository.save(booking.getTrip());
    booking.setCancelledBy(Role.USER);
    booking.setCancelledAt(Instant.now());
    bookingRepository.save(booking);
    return TripMapper.toBookingResponse(booking);
  }

  public void completeRefund(String bookingReference) {
  }

  @Transactional
  public void adminCancellation(Long tripId) {

    List<Booking> bookingList = bookingRepository.findByTripIdAndStatus(tripId, BookingStatus.CONFIRMED);

    for(Booking book : bookingList) {
      book.setStatus(BookingStatus.REFUND_INITIATED);
      book.setCancelledBy(Role.ADMIN);
      book.setCancelledAt(Instant.now());
    }
    bookingRepository.saveAll(bookingList);
  }

}
