package com.example.hotels.service;

import java.util.List;
import com.example.hotels.model.Reservas;

public interface ReservasService {
    Reservas crearReserva(Long idHotel, Reservas reserva);
    void cancelarReserva(Long idReserva);
    boolean consultarDisponibilidad(Long idHotel, String checkInDate, String checkOutDate);
    List<Reservas> obtenerReservasPorHotel(Long idHotel);
    Reservas actualizarReserva(Long idReserva, Reservas reservaActualizada);
}

