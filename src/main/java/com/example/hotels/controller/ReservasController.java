package com.example.hotels.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.hotels.exception.ResourceNotFoundException;

import com.example.hotels.model.Reservas;
import com.example.hotels.service.ReservasService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ReservasController {

    private static final Logger log = LoggerFactory.getLogger(ReservasController.class);

    @Autowired
    private ReservasService reservasService;

    @GetMapping("/hotels/{idHotel}/reservas")
    public ResponseEntity<List<Reservas>> getReservasPorHotel(@PathVariable("idHotel") Long idHotel) {
        log.info("Obteniendo reservas para el hotel ID: {}", idHotel);
        List<Reservas> reservas = reservasService.obtenerReservasPorHotel(idHotel);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/hotels/{idHotel}/{idReserva}")
    public ResponseEntity<?> getReservaByIdHotelAndReserva(@PathVariable("idHotel") Long idHotel, @PathVariable("idReserva") Long idReserva) {
        log.info("Buscando reserva ID: {} en el hotel ID: {}", idReserva, idHotel);

        List<Reservas> reservas = reservasService.obtenerReservasPorHotel(idHotel);
        Optional<Reservas> reservaOpt = reservas.stream().filter(r -> r.getId() == idReserva).findFirst();
        
        if (reservaOpt.isPresent()) {
            return ResponseEntity.ok(reservaOpt.get());
        }
        throw new ResourceNotFoundException("No se encontro la reserva con id: " + idReserva + " en el Hotel con id: " + idHotel);
    }

    @PostMapping("/hotels/{idHotel}")
    public ResponseEntity<?> crearReserva(@PathVariable("idHotel") Long idHotel, @Valid @RequestBody Reservas reserva) {
        log.info("Creando nueva reserva para el huesped: {} en el hotel ID: {}", reserva.getGuestName(), idHotel);
        Reservas nuevaReserva = reservasService.crearReserva(idHotel, reserva);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @PutMapping("/reservas/{idReserva}")
    public ResponseEntity<?> actualizarReserva(@PathVariable("idReserva") Long idReserva, @Valid @RequestBody Reservas reserva) {
        log.info("Actualizando reserva ID: {}", idReserva);
        Reservas reservaActualizada = reservasService.actualizarReserva(idReserva, reserva);
        return ResponseEntity.ok(reservaActualizada);
    }

    @DeleteMapping("hotels/reservas/{idReserva}")
    public ResponseEntity<?> cancelarReserva(@PathVariable("idReserva") Long idReserva) {
        log.info("Cancelando reserva ID: {}", idReserva);
        reservasService.cancelarReserva(idReserva);
        return ResponseEntity.ok("Reserva cancelada exitosamente");
    }

    @GetMapping("/hotels/{idHotel}/disponibilidad")
    public ResponseEntity<?> verificarDisponibilidad(@PathVariable("idHotel") Long idHotel, @RequestParam("checkIn") String checkIn, @RequestParam("checkOut") String checkOut) {
        log.info("Verificando disponibilidad para el hotel ID: {} desde {} hasta {}", idHotel, checkIn, checkOut);
        boolean disponible = reservasService.consultarDisponibilidad(idHotel, checkIn, checkOut);
        Map<String, Object> response = new HashMap<>();
        response.put("hotelId", idHotel);
        response.put("disponible", disponible);
        response.put("mensaje", disponible ? "El hotel tiene disponibilidad para las fechas solicitadas" : "No hay habitaciones disponibles para esas fechas");
        return ResponseEntity.ok(response);
    }
}



