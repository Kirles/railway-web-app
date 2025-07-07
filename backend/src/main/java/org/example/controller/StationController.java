package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.dto.StationDTO;
import org.example.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public ResponseEntity<List<StationDTO>> getAllStations() {
        List<StationDTO> cities = stationService.getAllStations();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationDTO> getStationById(@PathVariable Long id) {
        StationDTO dto = stationService.getStationById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/by-name")
    public ResponseEntity<StationDTO> getStationByName(@RequestParam @NotBlank String name) {
        StationDTO dto = stationService.getStationByName(name);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/by-code")
    public ResponseEntity<StationDTO> getStationByCode(@RequestParam @NotBlank String code) {
        StationDTO dto = stationService.getStationByCode(code);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<StationDTO> createStation(@Valid @RequestBody StationDTO dto) {
        StationDTO createdStation  = stationService.createStation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationDTO> updateStation(@PathVariable Long id, @RequestBody StationDTO dto) {
        StationDTO updatedStation = stationService.updateStation(id, dto);
        return ResponseEntity.ok(updatedStation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

}
