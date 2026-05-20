package com.skanda.travels.repository;

import com.skanda.travels.model.TravelRoute;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRouteRepository extends JpaRepository<TravelRoute, Long> {

  @EntityGraph(attributePaths = {"stops"})
  @Override
  java.util.List<TravelRoute> findAll ();
}
