package com.skanda.travels.repository;

import com.skanda.travels.model.Offer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OfferRepository extends JpaRepository<Offer, Long> {

  Optional<Offer> findByCouponCodeIgnoreCase (String couponCode);

  @Query("SELECT o FROM Offer o WHERE o.active = true AND (o.validUntil IS NULL OR o.validUntil >= :today)")
  List<Offer> findCurrentlyActiveOffers (@Param("today") LocalDate today);

}
