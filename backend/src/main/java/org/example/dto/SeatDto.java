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
public class SeatDto {
    private Long id;

    @NotNull(message = "Номер места обязателен")
    @Min(value = 1, message = "Номер места должен быть больше 0")
    private Integer seatNumber;

    private Boolean isAvailable;

    private Long carId;
    private Integer carNumber;
    private String carType;
}
