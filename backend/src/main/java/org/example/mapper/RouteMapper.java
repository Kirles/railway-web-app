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

    @Mapping(target = "departureStation", source = "departureStation.name")
    @Mapping(target = "arrivalStation", source = "arrivalStation.name")
    @Mapping(target = "routeStations", ignore = true)
    @Mapping(target = "trips", ignore = true)
    RouteDTO toDTO(Route route);

    @Mapping(target = "departureStation", expression = "java(map(routeDTO.getDepartureStation(), stationRepository))")
    @Mapping(target = "arrivalStation", expression = "java(map(routeDTO.getArrivalStation(), stationRepository))")
    @Mapping(target = "routeStations", ignore = true)
    @Mapping(target = "trips", ignore = true)
    Route toEntity(RouteDTO routeDTO, @Context StationRepository stationRepository);

    default Station map(String stationName, @Context StationRepository stationRepository){
        if(stationName == null){
            return null;
        }
        return stationRepository.findByName(stationName)
                .orElseThrow(() -> new RuntimeException("Station not found: " + stationName));
    }

}
