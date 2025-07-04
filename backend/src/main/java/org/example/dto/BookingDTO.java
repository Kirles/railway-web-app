package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;

    @NotNull(message = "Рейс обязателен")
    private TripDTO trip;

    @NotNull(message = "Пассажир обязателен")
    private PassengerDTO passenger;

    @NotNull(message = "Место обязательно")
    private SeatDTO seat;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime bookingDate;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
    @Digits(integer = 8, fraction = 2, message = "Неверный формат цены")
    private BigDecimal price;
}
