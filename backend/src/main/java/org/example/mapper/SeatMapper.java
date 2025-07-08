package org.example.mapper;

import org.example.dto.SeatDTO;
import org.example.entities.Car;
import org.example.entities.Seat;
import org.example.repository.CarRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(target = "car", source = "car.id")
    @Mapping(target = "bookings", ignore = true)
    SeatDTO toDTO(Seat seat);

    @Mapping(target = "car", expression = "java(map(seatDTO.getCar(), carRepository))")
    @Mapping(target = "bookings", ignore = true)
    Seat toEntity(SeatDTO seatDTO, @Context CarRepository carRepository);

    default Car map(Long carId, @Context CarRepository carRepository) {
        if (carId == null) {
            return null;
        }
        return carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found: " + carId));
    }

}
