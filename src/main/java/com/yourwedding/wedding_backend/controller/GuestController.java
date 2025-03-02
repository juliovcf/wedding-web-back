package com.yourwedding.wedding_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourwedding.wedding_backend.dto.GuestDTO;
import com.yourwedding.wedding_backend.model.Guest;
import com.yourwedding.wedding_backend.service.GuestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guests")
@CrossOrigin(origins = "*") // Permitir solicitudes CORS desde cualquier origen
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    // Crear un nuevo invitado
    @PostMapping
    public ResponseEntity<Guest> createGuest(@RequestBody GuestDTO guestDTO) {
        try {
            Guest guest = guestService.createGuest(guestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(guest);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

    // Búsqueda flexible por término (nombre o apellido) usando POST con body
    @PostMapping("/search")
    public ResponseEntity<List<Guest>> searchGuests(@RequestBody Map<String, String> payload) {
        String searchTerm = payload.get("name");
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        
        List<Guest> guests = guestService.searchGuestsByNameOrSurname(searchTerm);
        return ResponseEntity.ok(guests);
    }
    
    // Obtener todos los invitados de un grupo
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Guest>> getGuestsByGroup(@PathVariable Long groupId) {
        List<Guest> guests = guestService.getGuestsByGroupId(groupId);
        return ResponseEntity.ok(guests);
    }
    
    // Actualizar un invitado
    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody GuestDTO guestDTO) {
        try {
            Guest updatedGuest = guestService.updateGuest(id, guestDTO);
            return ResponseEntity.ok(updatedGuest);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    // Actualización masiva de invitados
    @PutMapping("/batch-update")
    public ResponseEntity<List<Guest>> batchUpdateGuests(@RequestBody List<GuestDTO> guestDTOs) {
        try {
            List<Guest> updatedGuests = guestService.batchUpdateGuests(guestDTOs);
            return ResponseEntity.ok(updatedGuests);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    // Actualizar todos los invitados de un grupo
    @PutMapping("/group/{groupId}")
    public ResponseEntity<List<Guest>> updateGuestsByGroup(
            @PathVariable Long groupId,
            @RequestBody List<GuestDTO> guestDTOs) {
        try {
            List<Guest> updatedGuests = guestService.updateGuestsByGroup(groupId, guestDTOs);
            return ResponseEntity.ok(updatedGuests);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}