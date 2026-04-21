package com.example.hotels.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotels.model.Hotels;
import com.example.hotels.repository.HotelsRepository;

@Service
public class HotelsServicelmpl implements HotelsService {

    @Autowired
    private HotelsRepository hotelsRepository;

    @Override
    public List<Hotels> getAllHotels() {
        return hotelsRepository.findAll();
    }

    @Override
    public Optional<Hotels> getHotelById(Long id) {
        return hotelsRepository.findById(id);
    }

    @Override
    public Hotels saveHotel(Hotels hotel) {
        return hotelsRepository.save(hotel);
    }

    @Override
    public Hotels updateHotel(Long id, Hotels hotel) {
        if(hotelsRepository.existsById(id)) {
            hotel.setId(id);
            return hotelsRepository.save(hotel);
        }
        else {
            return null;
        }
    }

    @Override
    public void deleteHotelById(Long id) {
        hotelsRepository.deleteById(id);
    }
}

