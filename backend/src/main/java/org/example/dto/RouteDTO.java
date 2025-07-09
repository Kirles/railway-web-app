package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    private Long id;

    @NotBlank(message = "Название маршрута обязательно")
    @Size(max = 255, message = "Название маршрута не должно превышать 255 символов")
    private String name;

    @NotNull(message = "Станция отправления обязательна")
    private Long departureStation;

    @NotNull(message = "Станция прибытия обязательна")
    private Long arrivalStation;

    @Min(value = 1, message = "Расстояние должно быть больше 0")
    private Integer distanceKm;

    @Min(value = 1, message = "Время в пути должно быть больше 0")
    private Integer durationMinutes;

    private List<RouteStationDTO> routeStations;

    private List<TripDTO> trips;
}
