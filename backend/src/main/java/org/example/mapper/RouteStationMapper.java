package org.example.mapper;

import org.example.dto.RouteStationDTO;
import org.example.entities.RouteStation;
import org.example.entities.Station;
import org.example.repository.StationRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteStationMapper {

    @Mapping(target = "station", source = "station.name")
    //@Mapping(target = "route", source = "route.name")
    @Mapping(target = "route", ignore = true)
    RouteStationDTO toDTO(RouteStation routeStation);

    @Mapping(target = "station", expression = "java(map(routeStationDTO.getStation(), stationRepository))")
    @Mapping(target = "route", ignore = true)
    RouteStation toEntity(RouteStationDTO routeStationDTO, @Context StationRepository stationRepository);

    List<RouteStationDTO> toDTOList(List<Station> stations);

    default Station map(String stationName, @Context StationRepository stationRepository) {
        if (stationName == null) {
            return null;
        }
        return stationRepository.findByName(stationName)
                .orElseThrow(() -> new RuntimeException("Station not found: " + stationName));
    }

}
