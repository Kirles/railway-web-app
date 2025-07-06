package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.RouteDTO;
import org.example.dto.RouteStationDTO;
import org.example.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        List<RouteDTO> cities = routeService.getAllRoutes();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable Long id) {
        RouteDTO dto = routeService.getRouteById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RouteDTO> createRoute(@Valid @RequestBody RouteDTO dto) {
        RouteDTO createdCity  = routeService.createRoute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteDTO> updateRoute(@PathVariable Long id, @RequestBody RouteDTO dto) {
        RouteDTO updatedCity = routeService.updateRoute(id, dto);
        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }
}
