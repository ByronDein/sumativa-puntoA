package com.example.hotels.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "hotels")
public class Hotels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El nombre del hotel es obligatorio")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "La ubicación del hotel es obligatoria")
    @Column(name = "location")
    private String location;

    @NotNull(message = "El precio por noche es obligatorio")
    @Min(value = 1, message = "El precio por noche debe ser mayor a 0")
    @Column(name = "price_per_night")
    private double pricePerNight;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservas> reservas;


    public Long getId() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setReservas(List<Reservas> reservas) {
        this.reservas = reservas;
    }


    // private int id;
    // private String name;
    // private String location;
    // private double pricePerNight;
    // private List <Reservas> reservas;



    // public Hotels(int id, String name, String location, double pricePerNight, List<Reservas>reservas) {
    //     this.id = id;
    //     this.name = name;
    //     this.location = location;
    //     this.pricePerNight = pricePerNight;
    //     this.reservas = reservas;
    // }


    // public int getId() {
    //     return id;
    // }

    // public String getName() {
    //     return name;
    // }

    // public String getLocation() {
    //     return location;
    // }

    // public double getPricePerNight() {
    //     return pricePerNight;
    // }

    // public List<Reservas> getReservas() {
    //     return reservas;
    // }

    // public void addReserva(Reservas reserva) {
    //     this.reservas.add(reserva);
    // }

    // public Reservas getReservaById(int idReserva) {
    //     for (Reservas reserva : reservas) {
    //         if (reserva.getId() == idReserva) {
    //             return reserva;
    //         }
    //     }
    //     return null;
    // }

}

