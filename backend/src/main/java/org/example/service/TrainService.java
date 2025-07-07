package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.TrainDTO;
import org.example.entities.Train;
import org.example.mapper.TrainMapper;
import org.example.repository.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;


    public List<TrainDTO> getAllTrains() {
        return trainRepository.findAll().stream().map(trainMapper::toDTO).collect(Collectors.toList());
    }

    public TrainDTO getTrainById(Long id) {
        return trainRepository.findById(id)
                .map(trainMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Train not found with id " + id));
    }

    public TrainDTO createTrain(TrainDTO dto) {
        Train train = trainMapper.toEntity(dto);
        Train saved = trainRepository.save(train);
        return trainMapper.toDTO(saved);
    }

    public TrainDTO updateTrain(Long id, TrainDTO dto) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Train not found with id " + id));
        train.setId(dto.getId());
        train.setNumber(dto.getNumber());
        train.setName(dto.getName());
        train.setTrainType(dto.getTrainType());
        train.setTotalSeats(dto.getTotalSeats());
        return trainMapper.toDTO(trainRepository.save(train));
    }

    public void deleteTrain(Long id) {
        if (!trainRepository.existsById(id)) {
            throw new EntityNotFoundException("Train not found with id " + id);
        }
        trainRepository.deleteById(id);
    }

}
