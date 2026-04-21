package com.example.hotels.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnore
    private Hotels hotel;

    @NotBlank(message = "El nombre del huésped es obligatorio")
    @Column(name = "guest_name")
    private String guestName;

    @NotBlank(message = "La fecha de ingreso (check in) es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de fecha debe ser YYYY-MM-DD")
    @Column(name = "check_in_date")
    private String checkInDate;

    @NotBlank(message = "La fecha de salida (check out) es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de fecha debe ser YYYY-MM-DD")
    @Column(name = "check_out_date")
    private String checkOutDate;

    public Hotels getHotel() {
        return hotel;
    }

    public long getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setHotel(Hotels hotel) {
        this.hotel = hotel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
   
    

    // private int id;
    // private String guestName;
    // private String checkInDate;
    // private String checkOutDate;

    // public Reservas(int id, String guestName, String checkInDate, String checkOutDate) {
    //     this.id = id;
    //     this.guestName = guestName;
    //     this.checkInDate = checkInDate;
    //     this.checkOutDate = checkOutDate;
    // }

    // public int getId() {
    //     return id;
    // }

    // public String getGuestName() {
    //     return guestName;
    // }

    // public String getCheckInDate() {
    //     return checkInDate;
    // }

    // public String getCheckOutDate() {
    //     return checkOutDate;
    // }


}

