package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.dto.CityDTO;
import org.example.entities.City;
import org.example.mapper.CityMapper;
import org.example.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public List<CityDTO> getAllCities(){
        return cityRepository.findAll().stream().map(cityMapper::toDTO).toList();
    }

    public CityDTO getCityById(Long id) {
        return cityRepository.findById(id)
                .map(cityMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id " + id));
    }

    public CityDTO getCityByName(String name) {
        return cityRepository.findByName(name)
                .map(cityMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id " + name));
    }

    public CityDTO createCity(CityDTO dto) {
        City city = cityMapper.toEntity(dto);
        City saved = cityRepository.save(city);
        return cityMapper.toDTO(saved);
    }

    public CityDTO updateCity(Long id, CityDTO dto) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id " + id));
        city.setName(dto.getName());
        city.setRegion(dto.getRegion());
        return cityMapper.toDTO(cityRepository.save(city));
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException("City not found with id " + id);
        }
        cityRepository.deleteById(id);
    }

}
