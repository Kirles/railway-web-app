package org.example.mapper;

import org.example.dto.BookingDTO;
import org.example.entities.*;
import org.example.repository.PassengerRepository;
import org.example.repository.RouteRepository;
import org.example.repository.StationRepository;
import org.example.repository.TripRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "trip", source = "trip.id")
    @Mapping(target = "passenger", source = "passenger.id")
    BookingDTO toDTO(Booking booking);

    @Mapping(target = "trip", expression = "java(mapTrip(bookingDTO.getTrip(), tripRepository))")
    @Mapping(target = "passenger", expression = "java(mapPassenger(bookingDTO.getPassenger(), passengerRepository))")
    Booking toEntity(BookingDTO bookingDTO,
                     @Context TripRepository tripRepository,
                     @Context PassengerRepository passengerRepository);

    default Trip mapTrip(Long tripId, @Context TripRepository tripRepository) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
    }

    default Passenger mapPassenger(Long passengerId, @Context PassengerRepository passengerRepository) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found with ID: " + passengerId));
    }
}
