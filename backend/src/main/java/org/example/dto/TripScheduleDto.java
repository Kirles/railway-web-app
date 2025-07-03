package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripScheduleDto {
    private Long id;

    @NotNull(message = "Станция обязательна")
    private StationDto station;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    @NotNull(message = "Порядок станции обязателен")
    @Min(value = 1, message = "Порядок станции должен быть больше 0")
    private Integer stationOrder;
}
