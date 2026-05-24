package com.skanda.travels.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.skanda.travels.entity.TravelRoute;

public interface TravelRouteRepository extends JpaRepository<TravelRoute, Long> {

  @EntityGraph(attributePaths = {"stops"})
  @Override
  java.util.List<TravelRoute> findAll ();
}
