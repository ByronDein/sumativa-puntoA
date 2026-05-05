package com.example.hotels.repository;

import com.example.hotels.model.Hotels;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class HotelsRepositoryTest {

    @Autowired
    private HotelsRepository hotelsRepository;

    @Test
    void saveAndFindByIdGuardaHotel() {
        Hotels hotel = new Hotels();
        hotel.setName("Hotel Repository Test");
        hotel.setLocation("Santiago");
        hotel.setPricePerNight(65000);

        Hotels guardado = hotelsRepository.save(hotel);
        Optional<Hotels> resultado = hotelsRepository.findById(guardado.getId());

        assertNotNull(guardado.getId());
        assertTrue(resultado.isPresent());
        assertEquals("Hotel Repository Test", resultado.get().getName());
    }
}
