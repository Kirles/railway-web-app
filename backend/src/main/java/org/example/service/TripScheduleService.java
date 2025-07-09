package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.TripScheduleDTO;
import org.example.entities.TripSchedule;
import org.example.mapper.TripScheduleMapper;
import org.example.repository.StationRepository;
import org.example.repository.TripRepository;
import org.example.repository.TripScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripScheduleService {

    private final TripScheduleRepository tripScheduleRepository;
    private final TripRepository tripRepository;
    private final StationRepository stationRepository;
    private final TripScheduleMapper tripScheduleMapper;

    public List<TripScheduleDTO> getAllTripSchedules() {
        return tripScheduleRepository.findAll().stream().map(tripScheduleMapper::toDTO).toList();
    }

    public TripScheduleDTO getTripScheduleById(Long id) {
        return tripScheduleRepository.findById(id)
                .map(tripScheduleMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Trip Schedule not found with id " + id));
    }

    public TripScheduleDTO createTripSchedule(TripScheduleDTO dto) {
        TripSchedule tripSchedule = tripScheduleMapper.toEntity(dto, tripRepository, stationRepository);
        TripSchedule saved = tripScheduleRepository.save(tripSchedule);
        return tripScheduleMapper.toDTO(saved);
    }

    public TripScheduleDTO updateTripSchedule(Long id, TripScheduleDTO dto) {
        TripSchedule tripSchedule = tripScheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip Schedule not found with id " + id));
        return tripScheduleMapper.toDTO(tripScheduleRepository.save(tripSchedule));
    }

    public void deleteTripSchedule(Long id) {
        if (!tripScheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Trip Schedule not found with id " + id);
        }
        tripScheduleRepository.deleteById(id);
    }

}
