package com.example.hotels;


public class Reservas {
    private int id;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;

    public Reservas(int id, String guestName, String checkInDate, String checkOutDate) {
        this.id = id;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getId() {
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


}
