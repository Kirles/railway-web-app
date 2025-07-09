package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookingDTO;
import org.example.entities.Booking;
import org.example.mapper.BookingMapper;
import org.example.mapper.PassengerMapper;
import org.example.mapper.SeatMapper;
import org.example.mapper.TripMapper;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final PassengerRepository passengerRepository;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final SeatRepository seatRepository;
    private final CarRepository carRepository;
    private final BookingMapper bookingMapper;
    private final TripMapper tripMapper;
    private final PassengerMapper passengerMapper;
    private final SeatMapper seatMapper;
    private final TripService tripService;
    private final PassengerService passengerService;
    private final SeatService seatService;

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(bookingMapper::toDTO).toList();
    }

    public BookingDTO getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id " + id));
    }

    public BookingDTO createBooking(BookingDTO dto) {
        Booking booking = bookingMapper.toEntity(dto, tripRepository, passengerRepository, seatRepository);
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDTO(saved);
    }

    public BookingDTO updateBooking(Long id, BookingDTO dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id " + id));
        booking.setTrip(tripMapper.toEntity(tripService.getTripById(dto.getTrip()), trainRepository, routeRepository));
        booking.setPassenger(passengerMapper.toEntity(passengerService.getPassengerById(dto.getPassenger())));
        booking.setSeat(seatMapper.toEntity(seatService.getSeatById(dto.getSeat()), carRepository));
        booking.setBookingDate(dto.getBookingDate());
        booking.setPrice(dto.getPrice());
        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new EntityNotFoundException("Booking not found with id " + id);
        }
        bookingRepository.deleteById(id);
    }
}
