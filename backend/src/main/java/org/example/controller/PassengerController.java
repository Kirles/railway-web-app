package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.PassengerDTO;
import org.example.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllCars() {
        List<PassengerDTO> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getCarById(@PathVariable Long id) {
        PassengerDTO passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passenger);
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> createCar(@Valid @RequestBody PassengerDTO dto) {
        PassengerDTO createdPassenger  = passengerService.createPassenger(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> updateCar(@PathVariable Long id, @RequestBody PassengerDTO dto) {
        PassengerDTO updatedPassenger = passengerService.updatePassenger(id, dto);
        return ResponseEntity.ok(updatedPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        passengerService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
