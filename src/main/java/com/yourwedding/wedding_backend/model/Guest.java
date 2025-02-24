package com.yourwedding.wedding_backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String surname;
    
    @Email(message = "Debe ser un email válido")
    private String email;
    
    private boolean confirmedAttendance = false;
    private String dietaryRestrictions;
    private String suggests;

    // Relación con el invitado principal (si es un acompañante)
    @ManyToOne
    @JoinColumn(name = "main_guest_id")
    private Guest mainGuest;

    // Lista de acompañantes (si es el invitado principal)
    @OneToMany(mappedBy = "mainGuest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> companions = new ArrayList<>();
}