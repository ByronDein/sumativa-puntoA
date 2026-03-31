package com.example.hotels;
import java.util.List;


public class Hotels {
    private int id;
    private String name;
    private String location;
    private double pricePerNight;
    private List <Reservas> reservas;



    public Hotels(int id, String name, String location, double pricePerNight, List<Reservas>reservas) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.reservas = reservas;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public List<Reservas> getReservas() {
        return reservas;
    }

    public void addReserva(Reservas reserva) {
        this.reservas.add(reserva);
    }

    public Reservas getReservaById(int idReserva) {
        for (Reservas reserva : reservas) {
            if (reserva.getId() == idReserva) {
                return reserva;
            }
        }
        return null;
    }

}
