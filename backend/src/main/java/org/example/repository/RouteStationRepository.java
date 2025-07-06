package org.example.repository;

import org.example.entities.Route;
import org.example.entities.RouteStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteStationRepository extends JpaRepository<RouteStation, Long> {
    Optional<RouteStation> findByRoute(Route route);
}
