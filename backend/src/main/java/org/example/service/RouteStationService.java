package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.RouteStationDTO;
import org.example.entities.RouteStation;
import org.example.mapper.CityMapper;
import org.example.mapper.RouteStationMapper;
import org.example.mapper.StationMapper;
import org.example.repository.CityRepository;
import org.example.repository.RouteRepository;
import org.example.repository.RouteStationRepository;
import org.example.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteStationService {

    private final RouteStationRepository routeStationRepository;
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;
    private final CityRepository cityRepository;
    private final StationService stationService;
    private final RouteStationMapper routeStationMapper;
    private final StationMapper stationMapper;

    public List<RouteStationDTO> getAllRouteStations() {
        return routeStationRepository.findAll().stream().map(routeStationMapper::toDTO).collect(Collectors.toList());
    }

    public RouteStationDTO getRouteStationById(Long id) {
        return routeStationRepository.findById(id)
                .map(routeStationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Route station not found with id " + id));
    }

    public RouteStationDTO createRouteStation(RouteStationDTO dto) {
        RouteStation station = routeStationMapper.toEntity(dto, stationRepository, routeRepository);
        RouteStation saved = routeStationRepository.save(station);
        return routeStationMapper.toDTO(saved);
    }

    public RouteStationDTO updateRouteStation(Long id, RouteStationDTO dto) {
        RouteStation station = routeStationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route station not found with id " + id));
        station.setStation(stationMapper.toEntity(stationService.getStationByName(dto.getStation()), cityRepository));
        return routeStationMapper.toDTO(routeStationRepository.save(station));
    }

    public void deleteRouteStation(Long id) {
        if (!routeStationRepository.existsById(id)) {
            throw new EntityNotFoundException("Route station not found with id " + id);
        }
        routeStationRepository.deleteById(id);
    }

}
