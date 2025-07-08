package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.dto.CarDTO;
import org.example.dto.CityDTO;
import org.example.service.CarService;
import org.example.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        CarDTO dto = carService.getCarById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@Valid @RequestBody CarDTO dto) {
        CarDTO createdCar  = carService.createCar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long id, @RequestBody CarDTO dto) {
        CarDTO updatedCity = carService.updateCar(id, dto);
        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
