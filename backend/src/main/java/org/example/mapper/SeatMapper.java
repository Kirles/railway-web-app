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

    @Mapping(target = "car", expression = "java(mapCar(seatDTO.getCar(), carRepository))")
    @Mapping(target = "bookings", ignore = true)
    Seat toEntity(SeatDTO seatDTO, @Context CarRepository carRepository);

    default Car mapCar(Long carId, @Context CarRepository carRepository) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found: " + carId));
    }

}
