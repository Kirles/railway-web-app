package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.TripScheduleDTO;
import org.example.entities.TripSchedule;
import org.example.mapper.StationMapper;
import org.example.mapper.TripMapper;
import org.example.mapper.TripScheduleMapper;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripScheduleService {

    private final TripScheduleRepository tripScheduleRepository;
    private final CityRepository cityRepository;
    private final TripRepository tripRepository;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final TripScheduleMapper tripScheduleMapper;
    private final TripMapper tripMapper;
    private final StationMapper stationMapper;
    private final TripService tripService;
    private final StationService stationService;

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
        tripSchedule.setTrip(tripMapper.toEntity(tripService.getTripById(dto.getTrip()), trainRepository, routeRepository));
        tripSchedule.setStation(stationMapper.toEntity(stationService.getStationById(dto.getStation()), cityRepository));
        tripSchedule.setArrivalTime(dto.getArrivalTime());
        tripSchedule.setDepartureTime(dto.getDepartureTime());
        tripSchedule.setStationOrder(dto.getStationOrder());
        return tripScheduleMapper.toDTO(tripScheduleRepository.save(tripSchedule));
    }

    public void deleteTripSchedule(Long id) {
        if (!tripScheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Trip Schedule not found with id " + id);
        }
        tripScheduleRepository.deleteById(id);
    }

}
