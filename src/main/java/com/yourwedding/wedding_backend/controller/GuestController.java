package com.yourwedding.wedding_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yourwedding.wedding_backend.dto.GuestDTO;
import com.yourwedding.wedding_backend.model.Guest;
import com.yourwedding.wedding_backend.service.GuestService;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    // Crear un nuevo invitado
    @PostMapping
    public ResponseEntity<String> createGuest(@RequestBody GuestDTO guestDTO) {
        try {
            Guest guest = guestService.createGuest(guestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Invitado creado con ID: " + guest.getId());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Obtener todos los invitados principales
    @GetMapping
    public ResponseEntity<List<Guest>> getAllMainGuests() {
        List<Guest> guests = guestService.getAllMainGuests();
        return ResponseEntity.ok(guests);
    }

    // Obtener un invitado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        try {
            Guest guest = guestService.getGuestById(id);
            return ResponseEntity.ok(guest);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

        // Buscar invitados por nombre y apellido
    @GetMapping("/search")
    public ResponseEntity<List<Guest>> searchGuestsByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname) {
        List<Guest> guests = guestService.findGuestsByNameAndSurname(name, surname);
        return ResponseEntity.ok(guests);
    }
}