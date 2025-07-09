package org.example.mapper;

import org.example.dto.CityDTO;
import org.example.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "stations", ignore = true)
    CityDTO toDTO(City city);

    @Mapping(target = "stations", ignore = true)
    City toEntity(CityDTO cityDTO);

}
