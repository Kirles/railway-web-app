package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long id;

    @NotNull(message = "Поезд обязателен")
    private TripDTO train;

    @NotNull(message = "Маршрут обязателен")
    private RouteDTO route;

    @NotNull(message = "Дата отправления обязательна")
    @Future(message = "Дата отправления должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @NotNull(message = "Время отправления обязательно")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    @NotNull(message = "Дата прибытия обязательна")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;

    @NotNull(message = "Время прибытия обязательно")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;

    @Min(value = 0, message = "Количество доступных мест не может быть отрицательным")
    private Integer availableSeats;

    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
    @Digits(integer = 8, fraction = 2, message = "Неверный формат цены")
    private BigDecimal basePrice;

    private List<BookingDTO> bookings;

    private List<TripScheduleDTO> tripSchedules;
}
