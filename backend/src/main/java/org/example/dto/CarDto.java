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
public class CarDto {
    private Long id;

    @NotBlank(message = "Тип вагона обязателен")
    @Size(max = 100, message = "Тип вагона не должен превышать 100 символов")
    private String carType;

    @NotNull(message = "Номер вагона обязателен")
    @Min(value = 1, message = "Номер вагона должен быть больше 0")
    private Integer carNumber;

    @NotNull(message = "Количество мест обязательно")
    @Min(value = 1, message = "Количество мест должно быть больше 0")
    private Integer totalSeats;

    private List<SeatDto> seats;
}
