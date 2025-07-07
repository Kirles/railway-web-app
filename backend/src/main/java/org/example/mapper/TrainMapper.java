package org.example.mapper;

import org.example.dto.TrainDTO;
import org.example.entities.Train;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrainMapper{

    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "trips", ignore = true)
    TrainDTO toDTO(Train train);

    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "trips", ignore = true)
    Train toEntity(TrainDTO trainDTO);

}
