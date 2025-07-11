package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.CarDTO;
import org.example.entities.Car;
import org.example.mapper.CarMapper;
import org.example.mapper.TrainMapper;
import org.example.repository.CarRepository;
import org.example.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final TrainRepository trainRepository;
    private final CarMapper carMapper;
    private final TrainMapper trainMapper;
    private final TrainService trainService;

    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream().map(carMapper::toDTO).toList();
    }

    public CarDTO getCarById(Long id) {
        return carRepository.findById(id)
                .map(carMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + id));
    }

    public CarDTO createCar(CarDTO dto) {
        Car car = carMapper.toEntity(dto, trainRepository);
        Car saved = carRepository.save(car);
        return carMapper.toDTO(saved);
    }

    public CarDTO updateCar(Long id, CarDTO dto) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + id));
        car.setTrain(trainMapper.toEntity(trainService.getTrainById(dto.getTrain())));
        car.setCarType(dto.getCarType());
        car.setCarNumber(dto.getCarNumber());
        car.setTotalSeats(dto.getTotalSeats());
        return carMapper.toDTO(carRepository.save(car));
    }

    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException("Car not found with id " + id);
        }
        carRepository.deleteById(id);
    }

}
