package com.example.hotels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class HotelsController {
    private List<Hotels> hotels = new ArrayList<>();

    public HotelsController() {
        // Inicializar la lista con datos de los hoteles

        hotels.add(new Hotels(1, "Hotel A", "Ciudad A", 100.0,
                new ArrayList<>(Arrays.asList(
                        new Reservas(1, "byron", "28-03-2026", "01-04-2026"),
                        new Reservas(2, "maria", "15-04-2026", "20-04-2026"),
                        new Reservas(3, "juan", "10-05-2026", "15-05-2026"),
                        new Reservas(4, "Cristobal", "10-05-2026", "15-05-2026")

                ))));

        hotels.add(new Hotels(2, "Hotel B", "Ciudad B", 150.0,
                new ArrayList<>(Arrays.asList(
                        new Reservas(1, "ana", "01-04-2026", "05-04-2026"),
                        new Reservas(2, "luis", "10-04-2026", "15-04-2026"),
                        new Reservas(3, "carlos", "20-05-2026", "25-05-2026")

                ))));
    }

    @GetMapping("/hotels")
    public List<Hotels> getHotels() {
        return hotels;
    }

    @GetMapping("/hotels/{idHotel}/{idReserva}")
    public ResponseEntity<?> getReservaByIdHotelAndReserva(
            @PathVariable("idHotel") Integer idHotel,
            @PathVariable("idReserva") Integer idReserva) {
        for (Hotels hotel : hotels) {
            if (hotel.getId() == idHotel) {
                Reservas reserva = hotel.getReservaById(idReserva);
                if (reserva != null) {
                    Hotels hotelFiltrado = new Hotels(
                            hotel.getId(),
                            hotel.getName(),
                            hotel.getLocation(),
                            hotel.getPricePerNight(),
                            Arrays.asList(reserva));
                    return ResponseEntity.ok(hotelFiltrado);
                }
            }

        }
        Map<String, Object> response = new HashMap<>();
        response.put("id", "no encontrado");
        response.put("message", "No se encontró el hotel o la reserva  con los  IDs Hotel: " + idHotel  + "Reserva: "+ idReserva+  ". Verifique el ID e intente nuevamente.");
        response.put("status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/hotels/{idHotel}")
    public ResponseEntity<?> getHotelById(@PathVariable("idHotel") Integer idHotel) {
        for (Hotels hotel : hotels) {
            if (hotel.getId() == idHotel) {
                return ResponseEntity.ok(hotel);
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("id", "no encontrado");
        response.put("message","No se encontro el Hotel con el id :" + idHotel + ". Verifique el ID e intente nuevamente.");
        response.put("status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/hotels/{idHotel}")
    public String postReserva(@RequestBody Reservas reserva, @PathVariable("idHotel") Integer idHotel) {
        for (Hotels hotel : hotels) {
            if (hotel.getId() == idHotel) {
                hotel.addReserva(reserva);
                return "Reserva agregada al hotel con ID: " + idHotel;
            }
        }
        return "Hotel no encontrado";
    }

}
