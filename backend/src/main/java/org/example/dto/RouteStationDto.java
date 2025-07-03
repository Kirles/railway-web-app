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
public class RouteStationDto {
    private Long id;

    @NotNull(message = "Станция обязательна")
    private StationDto station;

    @NotNull(message = "Порядок станции обязателен")
    @Min(value = 1, message = "Порядок станции должен быть больше 0")
    private Integer stationOrder;

    @Min(value = 0, message = "Время стоянки не может быть отрицательным")
    private Integer stopDurationMinutes;
}
