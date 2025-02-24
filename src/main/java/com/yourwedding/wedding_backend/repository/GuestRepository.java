package com.yourwedding.wedding_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourwedding.wedding_backend.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    // Busca invitados principales (sin mainGuest)
    List<Guest> findByMainGuestIsNull();

    // Busca acompañantes de un invitado principal
    List<Guest> findByMainGuestId(Long mainGuestId);

    // Busca invitados por email
    Guest findByEmail(String email);
}