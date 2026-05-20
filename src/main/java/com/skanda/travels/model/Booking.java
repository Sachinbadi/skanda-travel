package com.skanda.travels.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, updatable = false)
  private String bookingReference;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id", nullable = false)
  private Trip trip;

  @Column(nullable = false)
  private Instant bookingTime = Instant.now();

  @Column(nullable = false)
  private Integer numberOfPassengers;

  @Column(nullable = false)
  private BigDecimal totalFare;

  private String couponCodeApplied;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookingStatus status = BookingStatus.CONFIRMED;

  @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BookingPassenger> passengers = new ArrayList<>();

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public String getBookingReference () {
    return bookingReference;
  }

  public void setBookingReference (String bookingReference) {
    this.bookingReference = bookingReference;
  }

  public User getUser () {
    return user;
  }

  public void setUser (User user) {
    this.user = user;
  }

  public Trip getTrip () {
    return trip;
  }

  public void setTrip (Trip trip) {
    this.trip = trip;
  }

  public Instant getBookingTime () {
    return bookingTime;
  }

  public void setBookingTime (Instant bookingTime) {
    this.bookingTime = bookingTime;
  }

  public Integer getNumberOfPassengers () {
    return numberOfPassengers;
  }

  public void setNumberOfPassengers (Integer numberOfPassengers) {
    this.numberOfPassengers = numberOfPassengers;
  }

  public BigDecimal getTotalFare () {
    return totalFare;
  }

  public void setTotalFare (BigDecimal totalFare) {
    this.totalFare = totalFare;
  }

  public String getCouponCodeApplied () {
    return couponCodeApplied;
  }

  public void setCouponCodeApplied (String couponCodeApplied) {
    this.couponCodeApplied = couponCodeApplied;
  }

  public BookingStatus getStatus () {
    return status;
  }

  public void setStatus (BookingStatus status) {
    this.status = status;
  }

  public List<BookingPassenger> getPassengers () {
    return passengers;
  }

  public void setPassengers (List<BookingPassenger> passengers) {
    this.passengers = passengers;
  }

  @PrePersist
  void generateBookingReference () {
    if (bookingReference == null) {
      bookingReference = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
    if (bookingTime == null) {
      bookingTime = Instant.now();
    }
  }
}
