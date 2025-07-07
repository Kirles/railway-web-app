package org.example.mapper;

import org.example.dto.CityDTO;
import org.example.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring", uses = StationMapper.class)
public interface CityMapper {

    @Named("toDTOWithoutStations")
    @Mapping(target = "stations", ignore = true)
    CityDTO toDTO(City city);

    @Mapping(target = "stations", ignore = true)
    City toEntity(CityDTO cityDTO);

    @Mapping(target = "stations", source = "stations")
    CityDTO toDTOWithStations(City city);

}
