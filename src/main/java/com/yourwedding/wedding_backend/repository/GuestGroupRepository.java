package com.yourwedding.wedding_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yourwedding.wedding_backend.model.GuestGroup;

public interface GuestGroupRepository extends JpaRepository<GuestGroup, Long>{
    
}
