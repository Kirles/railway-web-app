package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.StationDTO;
import org.example.entities.Station;
import org.example.mapper.CityMapper;
import org.example.mapper.StationMapper;
import org.example.repository.CityRepository;
import org.example.repository.StationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;
    private final CityRepository cityRepository;
    private final CityService cityService;
    private final StationMapper stationMapper;
    private final CityMapper cityMapper;

    public List<StationDTO> getAllStations() {
        return stationRepository.findAll().stream().map(stationMapper::toDTO).collect(Collectors.toList());
    }

    public StationDTO getStationById(Long id) {
        return stationRepository.findById(id)
                .map(stationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id " + id));
    }

    public StationDTO getStationByName(String name) {
        return stationRepository.findByName(name)
                .map(stationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with name " + name));
    }

    public StationDTO getStationByCode(String code) {
        return stationRepository.findByCode(code)
                .map(stationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with name " + code));
    }

    public StationDTO createStation(StationDTO dto) {
        Station station = stationMapper.toEntity(dto, cityRepository);
        Station saved = stationRepository.save(station);
        return stationMapper.toDTO(saved);
    }

    public StationDTO updateStation(Long id, StationDTO dto) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id " + id));
        station.setName(dto.getName());
        station.setCode(dto.getCode());
        station.setCity(cityMapper.toEntity(cityService.getCityByName(dto.getCity())));
        station.setAddress(dto.getAddress());
        return stationMapper.toDTO(stationRepository.save(station));
    }

    public void deleteStation(Long id) {
        if (!stationRepository.existsById(id)) {
            throw new EntityNotFoundException("Station not found with id " + id);
        }
        stationRepository.deleteById(id);
    }

}
