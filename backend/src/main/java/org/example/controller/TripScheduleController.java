package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.TripScheduleDTO;
import org.example.service.TripScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tripSchedules/")
@RequiredArgsConstructor
public class TripScheduleController {

    private final TripScheduleService tripScheduleService;

    @GetMapping
    public ResponseEntity<List<TripScheduleDTO>> getAllBookings() {
        List<TripScheduleDTO> tripSchedules = tripScheduleService.getAllTripSchedules();
        return ResponseEntity.ok(tripSchedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripScheduleDTO> getBookingById(@PathVariable Long id) {
        TripScheduleDTO tripSchedule = tripScheduleService.getTripScheduleById(id);
        return ResponseEntity.ok(tripSchedule);
    }

    @PostMapping
    public ResponseEntity<TripScheduleDTO> createBooking(@Valid @RequestBody TripScheduleDTO dto) {
        TripScheduleDTO createdTripSchedule  = tripScheduleService.createTripSchedule(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTripSchedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripScheduleDTO> updateBooking(@PathVariable Long id, @RequestBody TripScheduleDTO dto) {
        TripScheduleDTO updatedTripSchedule = tripScheduleService.updateTripSchedule(id, dto);
        return ResponseEntity.ok(updatedTripSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        tripScheduleService.deleteTripSchedule(id);
        return ResponseEntity.noContent().build();
    }

}
