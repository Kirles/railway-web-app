package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.PassengerDTO;
import org.example.entities.Passenger;
import org.example.mapper.PassengerMapper;
import org.example.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public List<PassengerDTO> getAllPassengers() {
        return passengerRepository.findAll().stream().map(passengerMapper::toDTO).toList();
    }

    public PassengerDTO getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
    }

    public PassengerDTO createPassenger(PassengerDTO dto) {
        Passenger passenger = passengerMapper.toEntity(dto);
        Passenger saved = passengerRepository.save(passenger);
        return passengerMapper.toDTO(saved);
    }

    public PassengerDTO updatePassenger(Long id, PassengerDTO dto) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found with id " + id));
        return passengerMapper.toDTO(passengerRepository.save(passenger));
    }

    public void deleteCar(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new EntityNotFoundException("Car not found with id " + id);
        }
        passengerRepository.deleteById(id);
    }
}
