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
public class CityDto {
    private Long id;

    @NotBlank(message = "Название города обязательно")
    @Size(max = 255, message = "Название города не должно превышать 255 символов")
    private String name;

    @Size(max = 255, message = "Название региона не должно превышать 255 символов")
    private String region;

    private List<StationDto> stations;
}