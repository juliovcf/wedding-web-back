package com.yourwedding.wedding_backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yourwedding.wedding_backend.model.GuestGroup;
import com.yourwedding.wedding_backend.repository.GuestGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestGroupService {
    
    private final GuestGroupRepository guestGroupRepository;
    
    public GuestGroup getGroupById(Long id) {
        return guestGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo de invitados no encontrado"));
    }
    
    public Optional<GuestGroup> findGroupById(Long id) {
        return guestGroupRepository.findById(id);
    }
}
