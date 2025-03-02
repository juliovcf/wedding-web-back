package com.yourwedding.wedding_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yourwedding.wedding_backend.dto.GuestDTO;
import com.yourwedding.wedding_backend.model.Guest;
import com.yourwedding.wedding_backend.repository.GuestRepository;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    // Crear un nuevo invitado
    public Guest createGuest(GuestDTO guestDTO) {
        Guest guest = new Guest();
        mapDTOToEntity(guestDTO, guest);

        return guestRepository.save(guest);
    }

    // Obtener todos los invitados principales
    public List<Guest> getAllMainGuests() {
        return guestRepository.findAll();
    }

    // Obtener un invitado por ID
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitado no encontrado"));
    }

    // Buscar invitados por nombre y apellido
    public List<Guest> findGuestsByNameAndSurname(String name, String surname) {
        return guestRepository.findByNameAndSurnameIgnoreCase(name, surname);
    }

    // Convertir DTO a Entidad
    private void mapDTOToEntity(GuestDTO dto, Guest entity) {
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setConfirmedAttendance(dto.isConfirmedAttendance());
        entity.setDietaryRestrictions(dto.getDietaryRestrictions());
        entity.setSuggests(dto.getSuggests());
    }
}