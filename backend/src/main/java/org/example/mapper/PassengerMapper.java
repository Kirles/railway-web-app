package org.example.mapper;

import org.example.dto.PassengerDTO;
import org.example.entities.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "bookings", ignore = true)
    PassengerDTO toDTO(Passenger passenger);

    @Mapping(target = "bookings", ignore = true)
    Passenger toEntity(PassengerDTO passengerDTO);

}
