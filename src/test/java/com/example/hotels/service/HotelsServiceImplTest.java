package com.example.hotels.service;

import com.example.hotels.model.Hotels;
import com.example.hotels.repository.HotelsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelsServiceImplTest {

    @Mock
    private HotelsRepository hotelsRepository;

    @InjectMocks
    private HotelsServicelmpl hotelsService;

    private Hotels hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotels();
        hotel.setId(1L);
        hotel.setName("Hotel Central");
        hotel.setLocation("Santiago");
        hotel.setPricePerNight(50000);
    }

    @Test
    void getAllHotelsRetornaListaDeHoteles() {
        when(hotelsRepository.findAll()).thenReturn(List.of(hotel));

        List<Hotels> resultado = hotelsService.getAllHotels();

        assertEquals(1, resultado.size());
        assertEquals("Hotel Central", resultado.get(0).getName());
    }

    @Test
    void getHotelByIdRetornaHotelCuandoExiste() {
        when(hotelsRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Optional<Hotels> resultado = hotelsService.getHotelById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Santiago", resultado.get().getLocation());
    }
}
