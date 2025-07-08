package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.SeatDTO;
import org.example.entities.Seat;
import org.example.mapper.CarMapper;
import org.example.mapper.SeatMapper;
import org.example.repository.CarRepository;
import org.example.repository.SeatRepository;
import org.example.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final CarRepository carRepository;
    private final TrainRepository trainRepository;
    private final SeatMapper seatMapper;
    private final CarMapper carMapper;
    private final CarService carService;

    public List<SeatDTO> getAllSeats() {
        return seatRepository.findAll().stream().map(seatMapper::toDTO).toList();
    }

    public SeatDTO getSeatById(Long id) {
        return seatRepository.findById(id)
                .map(seatMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id " + id));
    }

    public SeatDTO createSeat(SeatDTO dto) {
        Seat seat = seatMapper.toEntity(dto, carRepository);
        Seat saved = seatRepository.save(seat);
        return seatMapper.toDTO(saved);
    }

    public SeatDTO updateSeat(Long id, SeatDTO dto) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id " + id));
        seat.setCar(carMapper.toEntity(carService.getCarById(dto.getCar()), trainRepository));
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setIsAvailable(dto.getIsAvailable());
        //bookings
        return seatMapper.toDTO(seatRepository.save(seat));
    }

    public void deleteSeat(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new EntityNotFoundException("Seat not found with id " + id);
        }
        seatRepository.deleteById(id);
    }
}
