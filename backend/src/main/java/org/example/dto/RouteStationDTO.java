package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteStationDTO {
    private Long id;

    @NotNull(message = "Маршрут обязателен")
    private Long route;

    @NotNull(message = "Станция обязательна")
    private Long station;

    @NotNull(message = "Порядок станции обязателен")
    @Min(value = 1, message = "Порядок станции должен быть больше 0")
    private Integer stationOrder;

    @Min(value = 0, message = "Время стоянки не может быть отрицательным")
    private Integer stopDurationMinutes;
}
