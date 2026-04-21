package com.example.hotels.service;

import java.util.List;
import java.util.Optional;

import com.example.hotels.model.Hotels;

public interface HotelsService {
    List<Hotels> getAllHotels();
    Optional<Hotels> getHotelById(Long id);
    Hotels saveHotel(Hotels hotel);
    Hotels updateHotel(Long id, Hotels hotel);
    void deleteHotelById(Long id);
}

