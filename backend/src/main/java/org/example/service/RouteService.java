package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.RouteDTO;
import org.example.entities.Route;
import org.example.mapper.RouteMapper;
import org.example.mapper.StationMapper;
import org.example.repository.CityRepository;
import org.example.repository.RouteRepository;
import org.example.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final CityRepository cityRepository;
    private final StationRepository stationRepository;
    private final RouteMapper routeMapper;
    private final StationMapper stationMapper;
    private final StationService stationService;

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll().stream().map(routeMapper::toDTO).collect(Collectors.toList());
    }

    public RouteDTO getRouteById(Long id) {
        return routeRepository.findById(id)
                .map(routeMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with id " + id));
    }

    public RouteDTO getRouteByName(String name) {
        return routeRepository.findByName(name)
                .map(routeMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with name " + name));
    }

    public RouteDTO createRoute(RouteDTO dto) {
        Route route = routeMapper.toEntity(dto, stationRepository);
        Route saved = routeRepository.save(route);
        return routeMapper.toDTO(saved);
    }

    public RouteDTO updateRoute(Long id, RouteDTO dto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found with id " + id));
        route.setName(dto.getName());
        route.setDepartureStation(stationMapper.toEntity(stationService.getStationByName(dto.getDepartureStation()), cityRepository));
        route.setArrivalStation(stationMapper.toEntity(stationService.getStationByName(dto.getArrivalStation()), cityRepository));
        route.setDistanceKm(dto.getDistanceKm());
        route.setDurationMinutes(dto.getDurationMinutes());
        return routeMapper.toDTO(routeRepository.save(route));
    }

    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new EntityNotFoundException("Route not found with id " + id);
        }
        routeRepository.deleteById(id);
    }

}
