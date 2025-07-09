package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.RouteStationDTO;
import org.example.dto.TripDTO;
import org.example.entities.RouteStation;
import org.example.entities.Trip;
import org.example.mapper.RouteMapper;
import org.example.mapper.TrainMapper;
import org.example.mapper.TripMapper;
import org.example.repository.RouteRepository;
import org.example.repository.StationRepository;
import org.example.repository.TrainRepository;
import org.example.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final TrainService trainService;
    private final RouteService routeService;
    private final TripMapper tripMapper;
    private final TrainMapper trainMapper;
    private final RouteMapper routeMapper;

    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll().stream().map(tripMapper::toDTO).collect(Collectors.toList());
    }

    public TripDTO getTripById(Long id) {
        return tripRepository.findById(id)
                .map(tripMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + id));
    }

    public TripDTO createTrip(TripDTO dto) {
        Trip trip = tripMapper.toEntity(dto, trainRepository, routeRepository);
        Trip saved = tripRepository.save(trip);
        return tripMapper.toDTO(saved);
    }

    public TripDTO updateTrip(Long id, TripDTO dto) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + id));
        trip.setId(dto.getId());
        trip.setTrain(trainMapper.toEntity(trainService.getTrainById(dto.getTrain())));
        trip.setRoute(routeMapper.toEntity(routeService.getRouteById(dto.getRoute()), stationRepository));
        trip.setDepartureDate(dto.getDepartureDate());
        trip.setDepartureTime(dto.getDepartureTime());
        trip.setArrivalDate(dto.getArrivalDate());
        trip.setArrivalTime(dto.getArrivalTime());
        trip.setAvailableSeats(dto.getAvailableSeats());
        trip.setBasePrice(dto.getBasePrice());
        return tripMapper.toDTO(tripRepository.save(trip));
    }

    public void deleteTrip(Long id) {
        if (!tripRepository.existsById(id)) {
            throw new EntityNotFoundException("Trip not found with id " + id);
        }
        tripRepository.deleteById(id);
    }

}
