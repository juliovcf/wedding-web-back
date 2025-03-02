package com.yourwedding.wedding_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourwedding.wedding_backend.dto.GuestDTO;
import com.yourwedding.wedding_backend.model.Guest;
import com.yourwedding.wedding_backend.model.GuestGroup;
import com.yourwedding.wedding_backend.repository.GuestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestGroupService guestGroupService;

    // Crear un nuevo invitado
    public Guest createGuest(GuestDTO guestDTO) {
        Guest guest = new Guest();
        mapDTOToEntity(guestDTO, guest);

        // Si tiene ID de grupo, asociarlo
        if (guestDTO.getGroupGuestId() != null) {
            Guest groupGuest = getGuestById(guestDTO.getGroupGuestId());
            guest.setGroup(groupGuest.getGroup());
        }

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

    // Buscar invitados por nombre o apellido (búsqueda flexible)
    public List<Guest> searchGuestsByNameOrSurname(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return List.of();
        }
        return guestRepository.findByNameOrSurnameContainingIgnoreCase(searchTerm);
    }
    
    // Obtener todos los invitados de un grupo
    public List<Guest> getGuestsByGroupId(Long groupId) {
        return guestRepository.findByGroupId(groupId);
    }
    
    // Actualizar un invitado
    public Guest updateGuest(Long id, GuestDTO guestDTO) {
        Guest guest = getGuestById(id);
        mapDTOToEntity(guestDTO, guest);
        return guestRepository.save(guest);
    }
    
    // Actualización masiva de invitados
    @Transactional
    public List<Guest> batchUpdateGuests(List<GuestDTO> guestDTOs) {
        List<Guest> updatedGuests = guestDTOs.stream()
                .map(dto -> {
                    if (dto.getId() == null) {
                        // Si no tiene ID, crear nuevo
                        Guest newGuest = new Guest();
                        mapDTOToEntity(dto, newGuest);
                        
                        // Asignar grupo si existe
                        if (dto.getGroupGuestId() != null) {
                            GuestGroup group = guestGroupService.getGroupById(dto.getGroupGuestId());
                            newGuest.setGroup(group);
                        }
                        
                        return newGuest;
                    } else {
                        // Si tiene ID, actualizar existente
                        Guest guest = getGuestById(dto.getId());
                        mapDTOToEntity(dto, guest);
                        return guest;
                    }
                })
                .collect(Collectors.toList());
        
        return guestRepository.saveAll(updatedGuests);
    }
    
    // Actualizar todos los invitados de un grupo
    @Transactional
    public List<Guest> updateGuestsByGroup(Long groupId, List<GuestDTO> guestDTOs) {
        // Verificar que el grupo existe
        GuestGroup group = guestGroupService.getGroupById(groupId);
        System.out.println("Actualizando grupo: " + group.getId() + " - " + group.getName());
        
        // Obtener los invitados actuales del grupo
        List<Guest> existingGuests = guestRepository.findByGroupId(groupId);
        System.out.println("Invitados existentes en el grupo: " + existingGuests.size());
        existingGuests.forEach(g -> System.out.println("Invitado existente: ID=" + g.getId() + ", Nombre=" + g.getName() + " " + g.getSurname()));
        
        // Mapear los DTOs a entidades y actualizar
        List<Guest> updatedGuests = new ArrayList<>();
        
        for (GuestDTO dto : guestDTOs) {
            System.out.println("Procesando DTO: nombre=" + dto.getName() + ", apellido=" + dto.getSurname() + ", ID=" + dto.getId());
            
            // Si el ID es null, es un nuevo invitado
            if (dto.getId() == null) {
                System.out.println("Creando nuevo invitado porque ID es null");
                Guest newGuest = new Guest();
                newGuest.setGroup(group);
                mapDTOToEntity(dto, newGuest);
                updatedGuests.add(newGuest);
                continue;
            }
            
            // Buscar el invitado por ID
            Optional<Guest> existingGuestOpt = guestRepository.findById(dto.getId());
            
            if (existingGuestOpt.isPresent()) {
                System.out.println("Encontrado invitado existente con ID: " + dto.getId());
                Guest existingGuest = existingGuestOpt.get();
                mapDTOToEntity(dto, existingGuest);
                updatedGuests.add(existingGuest);
            } else {
                System.out.println("No se encontró invitado con ID: " + dto.getId() + ". Creando uno nuevo.");
                Guest newGuest = new Guest();
                newGuest.setGroup(group);
                mapDTOToEntity(dto, newGuest);
                updatedGuests.add(newGuest);
            }
        }
        
        System.out.println("Total de invitados a guardar: " + updatedGuests.size());
        return guestRepository.saveAll(updatedGuests);
    }

    // Convertir DTO a Entidad
    private void mapDTOToEntity(GuestDTO dto, Guest entity) {
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setConfirmedAttendance(dto.getConfirmedAttendance());
        entity.setKid(dto.isKid());
        entity.setDietaryRestrictions(dto.getDietaryRestrictions());
        entity.setSuggests(dto.getSuggests());
    }
}