package org.example.mapper;

import org.example.dto.StationDTO;
import org.example.entities.City;
import org.example.entities.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    @Mapping(target = "city", source = "city.name")
    StationDTO toDTO(Station station);

    @Mapping(target = "city", ignore = true)
    @Mapping(target = "routeStations", ignore = true)
    Station toEntity(StationDTO stationDTO);

    List<StationDTO> toDTOList(List<Station> stations);

    default String map(City city) {
        return city != null ? city.getName() : null;
    }
}
