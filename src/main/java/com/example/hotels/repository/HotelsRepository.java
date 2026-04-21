package com.example.hotels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotels.model.Hotels;

@Repository
public interface HotelsRepository extends JpaRepository<Hotels, Long> {
}

