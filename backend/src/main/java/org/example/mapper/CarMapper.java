package org.example.mapper;

import org.example.dto.CarDTO;
import org.example.entities.Car;
import org.example.entities.Train;
import org.example.repository.TrainRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {

    @Mapping(target = "train", source = "train.name")
    @Mapping(target = "seats", ignore = true)
    CarDTO toDTO(Car car);

    @Mapping(target = "train", expression = "java(map(carDTO.getTrain(), trainRepository))")
    @Mapping(target = "seats", ignore = true)
    Car toEntity(CarDTO carDTO, @Context TrainRepository trainRepository);

    default Train map(String trainNumber, @Context TrainRepository trainRepository) {
        if (trainNumber == null) {
            return null;
        }
        return trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new RuntimeException("Train not found: " + trainNumber));
    }
}
