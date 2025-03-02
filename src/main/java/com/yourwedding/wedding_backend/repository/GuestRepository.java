package com.yourwedding.wedding_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yourwedding.wedding_backend.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    // Obtener todos los invitados de un grupo
    List<Guest> findByGroupId(Long groupId);

    // Buscar invitados por nombre o apellido (búsqueda flexible)
    @Query("SELECT g FROM Guest g WHERE LOWER(g.name) LIKE LOWER(concat('%', :term, '%')) OR LOWER(g.surname) LIKE LOWER(concat('%', :term, '%'))")
    List<Guest> findByNameOrSurnameContainingIgnoreCase(@Param("term") String term);
}