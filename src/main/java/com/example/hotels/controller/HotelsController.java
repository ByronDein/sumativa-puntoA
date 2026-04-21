package com.example.hotels.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.hotels.exception.ResourceNotFoundException;

import com.example.hotels.model.Hotels;
import com.example.hotels.service.HotelsService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HotelsController {

    private static final Logger log = LoggerFactory.getLogger(HotelsController.class);

    @Autowired
    private HotelsService hotelsService;

    @GetMapping("/hotels")
    public ResponseEntity<List<Hotels>> getAllHotels() {
        log.info("Obteniendo la lista de todos los hoteles");
        List<Hotels> list = hotelsService.getAllHotels();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/hotels/{idHotel}")
    public ResponseEntity<?> getHotelById(@PathVariable("idHotel") Long idHotel) {
        log.info("Buscando hotel con ID: {}", idHotel);
        Optional<Hotels> hotelOpt = hotelsService.getHotelById(idHotel);
        if (hotelOpt.isPresent()) {
            return ResponseEntity.ok(hotelOpt.get());
        }

        throw new ResourceNotFoundException("No se encontro el Hotel con el id :" + idHotel + ". Verifique el ID e intente nuevamente.");
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotels> createHotel(@Valid @RequestBody Hotels hotel) {
        log.info("Creando un nuevo hotel: {}", hotel.getName());
        Hotels savedHotel = hotelsService.saveHotel(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @PutMapping("/hotels/{idHotel}")
    public ResponseEntity<?> updateHotel(@PathVariable("idHotel") Long idHotel, @Valid @RequestBody Hotels hotel) {
        log.info("Actualizando hotel con ID: {}", idHotel);
        Hotels updatedHotel = hotelsService.updateHotel(idHotel, hotel);
        if (updatedHotel != null) {
            return ResponseEntity.ok(updatedHotel);
        }

        throw new ResourceNotFoundException("No se pudo actualizar el Hotel. Verifique el ID.");
    }

    @DeleteMapping("/hotels/{idHotel}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("idHotel") Long idHotel) {
        log.info("Eliminando hotel con ID: {}", idHotel);
        hotelsService.deleteHotelById(idHotel);
        return ResponseEntity.noContent().build();
    }
}



