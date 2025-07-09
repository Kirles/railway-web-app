package org.example.mapper;

import org.example.dto.BookingDTO;
import org.example.entities.*;
import org.example.repository.*;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "trip", source = "trip.id")
    @Mapping(target = "passenger", source = "passenger.id")
    @Mapping(target = "seat", source = "seat.id")
    BookingDTO toDTO(Booking booking);

    @Mapping(target = "trip", expression = "java(mapTrip(bookingDTO.getTrip(), tripRepository))")
    @Mapping(target = "passenger", expression = "java(mapPassenger(bookingDTO.getPassenger(), passengerRepository))")
    @Mapping(target = "seat", expression = "java(mapSeat(bookingDTO.getSeat(), seatRepository))")
    Booking toEntity(BookingDTO bookingDTO,
                     @Context TripRepository tripRepository,
                     @Context PassengerRepository passengerRepository,
                     @Context SeatRepository seatRepository);

    default Trip mapTrip(Long tripId, @Context TripRepository tripRepository) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
    }

    default Passenger mapPassenger(Long passengerId, @Context PassengerRepository passengerRepository) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found with ID: " + passengerId));
    }

    default Seat mapSeat(Long seatId, @Context SeatRepository seatRepository) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));
    }
}
