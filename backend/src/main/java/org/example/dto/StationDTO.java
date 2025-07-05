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
public class StationDTO {
    private Long id;

    @NotBlank(message = "Название станции обязательно")
    @Size(max = 255, message = "Название станции не должно превышать 255 символов")
    private String name;

    @NotBlank(message = "Код станции обязателен")
    @Size(max = 20, message = "Код станции не должен превышать 20 символов")
    private String code;

    @NotNull(message = "Город обязателен")
    private String city;

    @NotNull(message = "Адрес обязателен")
    private String address;

    private List<RouteStationDTO> routeStations;

    private List<TripScheduleDTO> tripSchedules;
}
