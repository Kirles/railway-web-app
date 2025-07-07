package org.example.mapper;

import org.example.dto.TripDTO;
import org.example.entities.Route;
import org.example.entities.Train;
import org.example.entities.Trip;
import org.example.repository.RouteRepository;
import org.example.repository.TrainRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "train", source = "train.number")
    @Mapping(target = "route", source = "route.name")
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "tripSchedules", ignore = true)
    TripDTO toDTO(Trip trip);

    @Mapping(target = "train", expression = "java(mapTrain(tripDTO.getTrain(), trainRepository))")
    @Mapping(target = "route", expression = "java(mapRoute(tripDTO.getRoute(), routeRepository))")
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "tripSchedules", ignore = true)
    Trip toEntity(TripDTO tripDTO,
                  @Context TrainRepository trainRepository,
                  @Context RouteRepository routeRepository);

    default Train mapTrain(String trainNumber, @Context TrainRepository trainRepository) {
        if (trainNumber == null) {
            return null;
        }
        return trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new RuntimeException("Train not found: " + trainNumber));
    }
    default Route mapRoute(String routeName, @Context RouteRepository routeRepository) {
        if (routeName == null) {
            return null;
        }
        return routeRepository.findByName(routeName)
                .orElseThrow(() -> new RuntimeException("Route not found: " + routeName));
    }
}
