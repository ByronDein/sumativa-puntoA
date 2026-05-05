package com.example.hotels.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotels.exception.ResourceNotFoundException;
import com.example.hotels.model.Reservas;
import com.example.hotels.service.ReservasService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ReservasController {

    private static final Logger log = LoggerFactory.getLogger(ReservasController.class);

    @Autowired
    private ReservasService reservasService;

    @GetMapping("/hotels/{idHotel}/reservas")
    public ResponseEntity<CollectionModel<EntityModel<Reservas>>> getReservasPorHotel(@PathVariable("idHotel") Long idHotel) {
        log.info("Obteniendo reservas para el hotel ID: {}", idHotel);
        List<EntityModel<Reservas>> reservas = reservasService.obtenerReservasPorHotel(idHotel).stream()
                .map(reserva -> toModel(idHotel, reserva))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(reservas,
                linkTo(methodOn(ReservasController.class).getReservasPorHotel(idHotel)).withSelfRel(),
                linkTo(methodOn(HotelsController.class).getHotelById(idHotel)).withRel("hotel")));
    }

    @GetMapping("/hotels/{idHotel}/{idReserva}")
    public ResponseEntity<EntityModel<Reservas>> getReservaByIdHotelAndReserva(
            @PathVariable("idHotel") Long idHotel,
            @PathVariable("idReserva") Long idReserva) {
        log.info("Buscando reserva ID: {} en el hotel ID: {}", idReserva, idHotel);

        List<Reservas> reservas = reservasService.obtenerReservasPorHotel(idHotel);
        Optional<Reservas> reservaOpt = reservas.stream()
                .filter(r -> Objects.equals(r.getId(), idReserva))
                .findFirst();

        if (reservaOpt.isPresent()) {
            return ResponseEntity.ok(toModel(idHotel, reservaOpt.get()));
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

    @DeleteMapping("/hotels/reservas/{idReserva}")
    public ResponseEntity<?> cancelarReserva(@PathVariable("idReserva") Long idReserva) {
        log.info("Cancelando reserva ID: {}", idReserva);
        reservasService.cancelarReserva(idReserva);
        return ResponseEntity.ok("Reserva cancelada exitosamente");
    }

    @GetMapping("/hotels/{idHotel}/disponibilidad")
    public ResponseEntity<?> verificarDisponibilidad(
            @PathVariable("idHotel") Long idHotel,
            @RequestParam("checkIn") String checkIn,
            @RequestParam("checkOut") String checkOut) {
        log.info("Verificando disponibilidad para el hotel ID: {} desde {} hasta {}", idHotel, checkIn, checkOut);
        boolean disponible = reservasService.consultarDisponibilidad(idHotel, checkIn, checkOut);
        Map<String, Object> response = new HashMap<>();
        response.put("hotelId", idHotel);
        response.put("disponible", disponible);
        response.put("mensaje", disponible ? "El hotel tiene disponibilidad para las fechas solicitadas" : "No hay habitaciones disponibles para esas fechas");
        return ResponseEntity.ok(response);
    }

    private EntityModel<Reservas> toModel(Long idHotel, Reservas reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservasController.class).getReservaByIdHotelAndReserva(idHotel, reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservasController.class).getReservasPorHotel(idHotel)).withRel("reservas"),
                linkTo(methodOn(HotelsController.class).getHotelById(idHotel)).withRel("hotel"));
    }
}
