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

    @Mapping(target = "station", source = "station.name")
    @Mapping(target = "route", source = "route.name")
    RouteStationDTO toDTO(RouteStation routeStation);

    @Mapping(target = "station", expression = "java(mapStation(routeStationDTO.getStation(), stationRepository))")
    @Mapping(target = "route", expression = "java(mapRoute(routeStationDTO.getRoute(), routeRepository))")
    RouteStation toEntity(RouteStationDTO routeStationDTO,
                          @Context StationRepository stationRepository,
                          @Context RouteRepository routeRepository);

    List<RouteStationDTO> toDTOList(List<Station> stations);

    default Station mapStation(String stationName, @Context StationRepository stationRepository) {
        if (stationName == null) {
            return null;
        }
        return stationRepository.findByName(stationName)
                .orElseThrow(() -> new RuntimeException("Station not found: " + stationName));
    }

    default Route mapRoute(String routeName, @Context RouteRepository routeRepository) {
        if (routeName == null) {
            return null;
        }
        return routeRepository.findByName(routeName)
                .orElseThrow(() -> new RuntimeException("Station not found: " + routeName));
    }

}
