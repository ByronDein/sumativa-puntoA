package com.example.hotels.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotels.model.Hotels;
import com.example.hotels.model.Reservas;
import com.example.hotels.repository.HotelsRepository;
import com.example.hotels.repository.ReservasRepository;

@Service
public class ReservasServiceImpl implements ReservasService {

    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private HotelsRepository hotelsRepository;

    @Override
    public Reservas crearReserva(Long idHotel, Reservas reserva) {
        Optional<Hotels> hotelOpt = hotelsRepository.findById(idHotel);
        if (hotelOpt.isPresent()) {
            reserva.setHotel(hotelOpt.get());
            if (consultarDisponibilidad(idHotel, reserva.getCheckInDate(), reserva.getCheckOutDate())) {
                return reservasRepository.save(reserva);
            } else {
                throw new RuntimeException("El hotel no tiene disponibilidad en esas fechas");
            }
        }
        throw new RuntimeException("Hotel no encontrado");
    }

    @Override
    public void cancelarReserva(Long idReserva) {
        reservasRepository.deleteById(idReserva);
    }

    @Override
    public Reservas actualizarReserva(Long idReserva, Reservas reservaActualizada) {
        Optional<Reservas> reservaOpt = reservasRepository.findById(idReserva); 
        if (reservaOpt.isPresent()) {
            Reservas reservaExistente = reservaOpt.get();
            Long idHotel = reservaExistente.getHotel().getId();

            boolean changedDates = !reservaExistente.getCheckInDate().equals(reservaActualizada.getCheckInDate()) 
                                || !reservaExistente.getCheckOutDate().equals(reservaActualizada.getCheckOutDate());

            if (changedDates) {
                List<Reservas> reservasDelHotel = obtenerReservasPorHotel(idHotel);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate in = LocalDate.parse(reservaActualizada.getCheckInDate(), formatter);
                LocalDate out = LocalDate.parse(reservaActualizada.getCheckOutDate(), formatter);

                for (Reservas r : reservasDelHotel) {
                    if (r.getId() == idReserva) continue; // Omitir la actual
                    LocalDate rIn = LocalDate.parse(r.getCheckInDate(), formatter);
                    LocalDate rOut = LocalDate.parse(r.getCheckOutDate(), formatter);   

                    if (in.isBefore(rOut) && out.isAfter(rIn)) {
                        throw new RuntimeException("El hotel no tiene disponibilidad en las nuevas fechas");
                    }
                }
            }

            reservaExistente.setGuestName(reservaActualizada.getGuestName());
            reservaExistente.setCheckInDate(reservaActualizada.getCheckInDate());
            reservaExistente.setCheckOutDate(reservaActualizada.getCheckOutDate());

            return reservasRepository.save(reservaExistente);
        }
        throw new RuntimeException("Reserva no encontrada");
    }

    @Override
    public List<Reservas> obtenerReservasPorHotel(Long idHotel) {
        // Obtenemos todas las reservas y filtramos (como no usamos @Query customizado)
        List<Reservas> todas = reservasRepository.findAll();
        List<Reservas> delHotel = new ArrayList<>();
        for (Reservas r : todas) {
            if (r.getHotel().getId().equals(idHotel)) {
                delHotel.add(r);
            }
        }
        return delHotel;
    }

    @Override
    public boolean consultarDisponibilidad(Long idHotel, String newCheckIn, String newCheckOut) {
        List<Reservas> reservasDelHotel = obtenerReservasPorHotel(idHotel);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate in = LocalDate.parse(newCheckIn, formatter);
        LocalDate out = LocalDate.parse(newCheckOut, formatter);

        for (Reservas r : reservasDelHotel) {
            LocalDate rIn = LocalDate.parse(r.getCheckInDate(), formatter);
            LocalDate rOut = LocalDate.parse(r.getCheckOutDate(), formatter);
            
            // Si la nueva fecha de entrada entra antes de que la otra salga
            // Y la nueva de salida es despues de que la otra entre = Hay choque
            if (in.isBefore(rOut) && out.isAfter(rIn)) {
                return false; // NO hay disponibilidad porque choca
            }
        }
        return true;
    }
}

