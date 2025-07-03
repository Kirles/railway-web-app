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
public class TrainDto {
    private Long id;

    @NotBlank(message = "Номер поезда обязателен")
    @Size(max = 20, message = "Номер поезда не должен превышать 20 символов")
    private String number;

    @Size(max = 255, message = "Название поезда не должно превышать 255 символов")
    private String name;

    @NotBlank(message = "Тип поезда обязателен")
    @Size(max = 100, message = "Тип поезда не должен превышать 100 символов")
    private String trainType;

    @Min(value = 1, message = "Количество мест должно быть больше 0")
    private Integer totalSeats;

    private List<CarDto> cars;
}
