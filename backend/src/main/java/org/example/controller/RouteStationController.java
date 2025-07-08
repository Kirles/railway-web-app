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
        List<RouteStationDTO> routeStations = routeStationService.getAllRouteStations();
        return ResponseEntity.ok(routeStations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteStationDTO> getRouteStationById(@PathVariable Long id) {
        RouteStationDTO routeStation = routeStationService.getRouteStationById(id);
        return ResponseEntity.ok(routeStation);
    }

    @PostMapping
    public ResponseEntity<RouteStationDTO> createRouteStation(@Valid @RequestBody RouteStationDTO dto) {
        RouteStationDTO createdRouteStation  = routeStationService.createRouteStation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRouteStation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteStationDTO> updateRouteStation(@PathVariable Long id, @RequestBody RouteStationDTO dto) {
        RouteStationDTO updatedRouteStation = routeStationService.updateRouteStation(id, dto);
        return ResponseEntity.ok(updatedRouteStation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRouteStation(@PathVariable Long id) {
        routeStationService.deleteRouteStation(id);
        return ResponseEntity.noContent().build();
    }

}
