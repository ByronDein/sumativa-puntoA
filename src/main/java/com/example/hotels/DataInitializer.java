package com.example.hotels;

import com.example.hotels.model.Hotels;
import com.example.hotels.model.Reservas;
import com.example.hotels.repository.HotelsRepository;
import com.example.hotels.repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private HotelsRepository hotelRepository;

    @Autowired
    private ReservasRepository reservaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpiamos los datos previos (Reservas primero por FK)
        reservaRepository.deleteAll();
        hotelRepository.deleteAll();

        // Creamos 3 Hoteles de prueba
        Hotels h1 = new Hotels();
        h1.setName("Gran Hotel Santiago");
        h1.setLocation("Santiago, Chile");
        h1.setPricePerNight(85.50);

        Hotels h2 = new Hotels();
        h2.setName("Marriott Viña del Mar");
        h2.setLocation("Viña del Mar, Chile");
        h2.setPricePerNight(150.00);

        Hotels h3 = new Hotels();
        h3.setName("Ibis Valparaíso");
        h3.setLocation("Valparaíso, Chile");
        h3.setPricePerNight(55.00);

        hotelRepository.save(h1);
        hotelRepository.save(h2);
        hotelRepository.save(h3);

        // Creamos una reserva para el primer hotel
        Reservas r1 = new Reservas();
        r1.setHotel(h1);
        r1.setGuestName("Byron Dein");
        r1.setCheckInDate("2026-05-01");
        r1.setCheckOutDate("2026-05-05");
        reservaRepository.save(r1);

        System.out.println("==================================================");
        System.out.println("✅ DATOS DE HOTELES INICIALIZADOS CON EXITO ✅");
        System.out.println("==================================================");
    }
}
