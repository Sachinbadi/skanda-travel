package com.skanda.travels.repository;

import com.skanda.travels.model.Trip;
import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, Long> {

  @EntityGraph(attributePaths = {"travelRoute", "travelRoute.stops", "bus"})
  @Query("""
      SELECT DISTINCT t FROM Trip t
      JOIN t.travelRoute r
      WHERE lower(trim(r.origin)) = lower(trim(:fromPlace))
      AND lower(trim(r.destination)) = lower(trim(:toPlace))
      AND t.departureTime >= :start
      AND t.departureTime < :endExclusive
      AND t.availableSeats >= :passengers
      ORDER BY t.departureTime ASC
      """)
  List<Trip> searchTrips (
      @Param("fromPlace") String fromPlace,
      @Param("toPlace") String toPlace,
      @Param("start") LocalDateTime start,
      @Param("endExclusive") LocalDateTime endExclusive,
      @Param("passengers") int passengers
  );

  @EntityGraph(attributePaths = {"travelRoute", "travelRoute.stops", "bus"})
  @Override
  Optional<Trip> findById (Long id);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @EntityGraph(attributePaths = {"travelRoute", "travelRoute.stops", "bus"})
  @Query("SELECT t FROM Trip t WHERE t.id = :id")
  Optional<Trip> findLockedById (@Param("id") Long id);
}
