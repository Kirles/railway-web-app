package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.RouteStationDTO;
import org.example.service.RouteStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routeStations")
@RequiredArgsConstructor
public class RouteStationController {

    private final RouteStationService routeStationService;

    @GetMapping
    public ResponseEntity<List<RouteStationDTO>> getAllRouteStations() {
        List<RouteStationDTO> cities = routeStationService.getAllRouteStations();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteStationDTO> getRouteStationById(@PathVariable Long id) {
        RouteStationDTO dto = routeStationService.getRouteStationById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RouteStationDTO> createRouteStation(@Valid @RequestBody RouteStationDTO dto) {
        RouteStationDTO createdCity  = routeStationService.createRouteStation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteStationDTO> updateRouteStation(@PathVariable Long id, @RequestBody RouteStationDTO dto) {
        RouteStationDTO updatedCity = routeStationService.updateRouteStation(id, dto);
        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouteStation(@PathVariable Long id) {
        routeStationService.deleteRouteStation(id);
        return ResponseEntity.noContent().build();
    }

}
