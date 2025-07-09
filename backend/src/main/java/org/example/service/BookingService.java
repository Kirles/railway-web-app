package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookingDTO;
import org.example.entities.Booking;
import org.example.mapper.BookingMapper;
import org.example.repository.BookingRepository;
import org.example.repository.PassengerRepository;
import org.example.repository.SeatRepository;
import org.example.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;
    private final BookingMapper bookingMapper;


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
        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new EntityNotFoundException("Booking not found with id " + id);
        }
        bookingRepository.deleteById(id);
    }
}
