package org.example.mapper;

import org.example.dto.StationDTO;
import org.example.entities.City;
import org.example.entities.Station;
import org.example.repository.CityRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    @Mapping(target = "city", source = "city.name")
    StationDTO toDTO(Station station);

    @Mapping(target = "city", expression = "java(map(stationDTO.getCity(), cityRepository))")
    @Mapping(target = "routeStations", ignore = true)
    @Mapping(target = "tripSchedules", ignore = true)
    Station toEntity(StationDTO stationDTO, @Context CityRepository cityRepository);

    List<StationDTO> toDTOList(List<Station> stations);

    default City map(String cityName, @Context CityRepository cityRepository) {
        if (cityName == null) {
            return null;
        }
        return cityRepository.findByName(cityName)
                .orElseThrow(() -> new RuntimeException("City not found: " + cityName));
    }

}
