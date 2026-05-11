package com.example.hotels.controller;

import com.example.hotels.exception.GlobalExceptionHandler;
import com.example.hotels.model.Hotels;
import com.example.hotels.service.HotelsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelsController.class)
@Import(GlobalExceptionHandler.class)
class HotelsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelsService hotelsService;

    @Test
    void getHotelByIdRetornaJsonConLinksHateoas() throws Exception {
        Hotels hotel = new Hotels();
        hotel.setId(1L);
        hotel.setName("Hotel Central");
        hotel.setLocation("Santiago");
        hotel.setPricePerNight(50000);

        when(hotelsService.getHotelById(1L)).thenReturn(Optional.of(hotel));

        mockMvc.perform(get("/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel Central"))
                .andExpect(jsonPath("$._links.self.href", containsString("/hotels/1")));
    }

    @Test
    void getHotelByIdRetornaNotFoundCuandoNoExiste() throws Exception {
        when(hotelsService.getHotelById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/hotels/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("No se encontro")));
    }

    @Test
    void getAllHotelsRetornaColeccionConLinks() throws Exception {
        Hotels hotel = new Hotels();
        hotel.setId(1L);
        hotel.setName("Hotel Central");
        hotel.setLocation("Santiago");
        hotel.setPricePerNight(50000);

        when(hotelsService.getAllHotels()).thenReturn(java.util.List.of(hotel));

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded..name", org.hamcrest.Matchers.hasItem("Hotel Central")))
                .andExpect(jsonPath("$._links.self.href", containsString("/hotels")));
    }

    @Test
    void createHotelRetornaCreatedConBody() throws Exception {
        Hotels hotel = new Hotels();
        hotel.setId(2L);
        hotel.setName("Hotel Norte");
        hotel.setLocation("Arica");
        hotel.setPricePerNight(45000);

        when(hotelsService.saveHotel(org.mockito.ArgumentMatchers.any(Hotels.class))).thenReturn(hotel);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/hotels")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Hotel Norte\",\"location\":\"Arica\",\"pricePerNight\":45000.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Hotel Norte"))
                .andExpect(jsonPath("$.location").value("Arica"));
    }
}
