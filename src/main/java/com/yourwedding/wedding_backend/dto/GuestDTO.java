package com.yourwedding.wedding_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuestDTO {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String surname;


    private Boolean confirmedAttendance;
    private boolean kid;
    private String dietaryRestrictions;
    private String suggests;
    private Boolean goingByBus;
    private String bus;

    private Long groupGuestId; // ID del invitado principal (si es un acompañante)
}