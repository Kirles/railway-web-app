package org.example.mapper;

import org.example.dto.TripScheduleDTO;
import org.example.entities.Station;
import org.example.entities.Trip;
import org.example.entities.TripSchedule;
import org.example.repository.StationRepository;
import org.example.repository.TripRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripScheduleMapper {

    @Mapping(target = "trip", source = "trip.id")
    @Mapping(target = "station", source = "station.id")
    TripScheduleDTO toDTO(TripSchedule tripSchedule);

    @Mapping(target = "trip", expression = "java(mapTrip(tripScheduleDTO.getTrip(), tripRepository))")
    @Mapping(target = "station", expression = "java(mapStation(tripScheduleDTO.getStation(), stationRepository))")
    TripSchedule toEntity(TripScheduleDTO tripScheduleDTO,
                          @Context TripRepository tripRepository,
                          @Context StationRepository stationRepository);

    default Trip mapTrip(Long tripId, @Context TripRepository tripRepository) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
    }

    default Station mapStation(Long stationId, @Context StationRepository stationRepository) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("Station not found with ID: " + stationId));
    }
}
