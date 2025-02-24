package com.yourwedding.wedding_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourwedding.wedding_backend.dto.GuestDTO;
import com.yourwedding.wedding_backend.model.Guest;
import com.yourwedding.wedding_backend.repository.GuestRepository;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    // Crear un nuevo invitado
    public Guest createGuest(GuestDTO guestDTO) {
        Guest guest = new Guest();
        mapDTOToEntity(guestDTO, guest);

        // Si es un acompañante, buscar el invitado principal
        if (guestDTO.getMainGuestId() != null) {
            Guest mainGuest = guestRepository.findById(guestDTO.getMainGuestId())
                    .orElseThrow(() -> new RuntimeException("Invitado principal no encontrado"));
            guest.setMainGuest(mainGuest);
        }

        return guestRepository.save(guest);
    }

    // Obtener todos los invitados principales
    public List<Guest> getAllMainGuests() {
        return guestRepository.findByMainGuestIsNull();
    }

    // Obtener un invitado por ID
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitado no encontrado"));
    }

    // Convertir DTO a Entidad
    private void mapDTOToEntity(GuestDTO dto, Guest entity) {
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setConfirmedAttendance(dto.isConfirmedAttendance());
        entity.setDietaryRestrictions(dto.getDietaryRestrictions());
        entity.setSuggests(dto.getSuggests());
    }
}