package com.yourwedding.wedding_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String surname;

    @Email(message = "Debe ser un email válido")
    private String email;

    private boolean confirmedAttendance;
    private String dietaryRestrictions;
    private String suggests;

    private Long mainGuestId; // ID del invitado principal (si es un acompañante)
}