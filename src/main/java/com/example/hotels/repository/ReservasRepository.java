package com.example.hotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotels.model.Reservas;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long> {
}

