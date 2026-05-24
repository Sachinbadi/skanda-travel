package com.skanda.travels.entity;

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
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.skanda.travels.enums.BookingStatus;
import com.skanda.travels.enums.Role;

@Entity @Getter @Setter
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
  private BookingStatus status;

  @Enumerated(EnumType.STRING)
  private Role cancelledBy; // null until cancellation happens

  private Instant cancelledAt; // null until cancellation happens

  private String razorpayOrderId;

  @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BookingPassenger> passengers = new ArrayList<>();

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
