package org.example.mapper;

import org.example.dto.StationDTO;
import org.example.entities.City;
import org.example.entities.Station;
import org.example.repository.CityRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StationMapper {

    @Mapping(target = "city", source = "city.id")
    @Mapping(target = "routeStations", ignore = true)
    @Mapping(target = "tripSchedules", ignore = true)
    StationDTO toDTO(Station station);

    @Mapping(target = "city", expression = "java(mapCity(stationDTO.getCity(), cityRepository))")
    @Mapping(target = "routeStations", ignore = true)
    @Mapping(target = "tripSchedules", ignore = true)
    Station toEntity(StationDTO stationDTO, @Context CityRepository cityRepository);

    default City mapCity(Long cityId, @Context CityRepository cityRepository) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found: " + cityId));
    }

}
