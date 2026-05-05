package com.example.hotels;

import com.example.hotels.model.Hotels;
import com.example.hotels.model.Reservas;
import com.example.hotels.repository.HotelsRepository;
import com.example.hotels.repository.ReservasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private HotelsRepository hotelRepository;

    @Autowired
    private ReservasRepository reservaRepository;

    @Override
    public void run(String... args) {
        if (hotelRepository.count() > 0) {
            System.out.println("Datos de hoteles ya existen. No se reinicializa la base.");
            return;
        }

        Hotels h1 = new Hotels();
        h1.setName("Gran Hotel Santiago");
        h1.setLocation("Santiago, Chile");
        h1.setPricePerNight(85.50);

        Hotels h2 = new Hotels();
        h2.setName("Marriott Vina del Mar");
        h2.setLocation("Vina del Mar, Chile");
        h2.setPricePerNight(150.00);

        Hotels h3 = new Hotels();
        h3.setName("Ibis Valparaiso");
        h3.setLocation("Valparaiso, Chile");
        h3.setPricePerNight(55.00);

        hotelRepository.save(h1);
        hotelRepository.save(h2);
        hotelRepository.save(h3);

        Reservas r1 = new Reservas();
        r1.setHotel(h1);
        r1.setGuestName("Byron Dein");
        r1.setCheckInDate("2026-05-01");
        r1.setCheckOutDate("2026-05-05");
        reservaRepository.save(r1);

        System.out.println("Datos de hoteles inicializados con exito.");
    }
}
