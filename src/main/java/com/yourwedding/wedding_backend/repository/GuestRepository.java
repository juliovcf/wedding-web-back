package com.yourwedding.wedding_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yourwedding.wedding_backend.model.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    // Obtener todos los invitados de un grupo
    List<Guest> findByGroupId(Long groupId);

    // Buscar invitados por nombre o apellido (búsqueda flexible, insensible a acentos)
    @Query(value = "SELECT * FROM guest WHERE unaccent(LOWER(name)) LIKE unaccent(LOWER(concat('%', :term, '%'))) OR unaccent(LOWER(surname)) LIKE unaccent(LOWER(concat('%', :term, '%')))", nativeQuery = true)
    List<Guest> findByNameOrSurnameContainingIgnoreCase(@Param("term") String term);

    long countByConfirmedAttendanceTrue();
    long countByConfirmedAttendanceFalse();
    long countByConfirmedAttendanceIsNull();
}