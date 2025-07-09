package org.example.mapper;

import org.example.dto.RouteStationDTO;
import org.example.entities.Route;
import org.example.entities.RouteStation;
import org.example.entities.Station;
import org.example.repository.RouteRepository;
import org.example.repository.StationRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteStationMapper {

    @Mapping(target = "station", source = "station.id")
    @Mapping(target = "route", source = "route.id")
    RouteStationDTO toDTO(RouteStation routeStation);

    @Mapping(target = "station", expression = "java(mapStation(routeStationDTO.getStation(), stationRepository))")
    @Mapping(target = "route", expression = "java(mapRoute(routeStationDTO.getRoute(), routeRepository))")
    RouteStation toEntity(RouteStationDTO routeStationDTO,
                          @Context StationRepository stationRepository,
                          @Context RouteRepository routeRepository);

    default Station mapStation(Long stationId, @Context StationRepository stationRepository) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found: " + stationId));
    }

    default Route mapRoute(Long routeId, @Context RouteRepository routeRepository) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found: " + routeId));
    }

}
