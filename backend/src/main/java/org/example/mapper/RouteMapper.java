package org.example.mapper;

import org.example.dto.RouteDTO;
import org.example.entities.Route;
import org.example.entities.Station;
import org.example.repository.StationRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    @Mapping(target = "departureStation", source = "departureStation.id")
    @Mapping(target = "arrivalStation", source = "arrivalStation.id")
    @Mapping(target = "routeStations", ignore = true)
    @Mapping(target = "trips", ignore = true)
    RouteDTO toDTO(Route route);

    @Mapping(target = "departureStation", expression = "java(mapStation(routeDTO.getDepartureStation(), stationRepository))")
    @Mapping(target = "arrivalStation", expression = "java(mapStation(routeDTO.getArrivalStation(), stationRepository))")
    @Mapping(target = "routeStations", ignore = true)
    @Mapping(target = "trips", ignore = true)
    Route toEntity(RouteDTO routeDTO, @Context StationRepository stationRepository);

    default Station mapStation(Long stationId, @Context StationRepository stationRepository){
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found: " + stationId));
    }

}
