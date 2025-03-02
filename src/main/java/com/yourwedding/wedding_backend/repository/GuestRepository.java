package com.yourwedding.wedding_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yourwedding.wedding_backend.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    // Obtener todos los invitados de un grupo
    List<Guest> findByGroupId(Long groupId);

    // Buscar invitados por nombre y apellido (ignorando mayúsculas/minúsculas)
    @Query("SELECT g FROM Guest g WHERE LOWER(g.name) LIKE LOWER(concat('%', :name, '%')) AND LOWER(g.surname) LIKE LOWER(concat('%', :surname, '%'))")
    List<Guest> findByNameAndSurnameIgnoreCase(@Param("name") String name, @Param("surname") String surname);
}