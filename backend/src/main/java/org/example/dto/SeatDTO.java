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
public class SeatDTO {
    private Long id;

    @NotNull(message = "Вагон обязателен")
    private Long car;

    @NotNull(message = "Номер места обязателен")
    @Min(value = 1, message = "Номер места должен быть больше 0")
    private Integer seatNumber;

    @Builder.Default
    private Boolean isAvailable = true;

    private List<BookingDTO> bookings;
}
