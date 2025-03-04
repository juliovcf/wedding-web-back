package com.yourwedding.wedding_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.yourwedding.wedding_backend.model.Guest;
import com.yourwedding.wedding_backend.repository.GuestRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final GuestRepository guestRepository;
    
    @Value("${app.wedding.admin.email:crisgavijupeca@gmail.com}")
    private String adminEmail;

    public void sendReservationUpdateNotification(List<Guest> updatedGuests, Long groupId) {
        log.info("Preparando notificación por email para actualización del grupo #{}", groupId);
        log.debug("Número de invitados actualizados: {}", updatedGuests.size());
        
        try {
            // Get statistics
            Map<String, Long> statistics = getGuestStatistics();
            log.info("Estadísticas obtenidas: Total={}, Confirmados={}, Rechazados={}, Pendientes={}",
                    statistics.get("total"), statistics.get("confirmed"), 
                    statistics.get("declined"), statistics.get("pending"));
            
            // Create the email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(adminEmail);
            message.setSubject("Nueva actualización de reserva - Grupo #" + groupId);
            log.debug("Enviando email a: {}", adminEmail);
            
            StringBuilder body = new StringBuilder();
            body.append("Se ha actualizado la información de un grupo de invitados:\n\n");
            
            // Add group details
            String groupName = "No disponible";
            if (!updatedGuests.isEmpty() && updatedGuests.get(0).getGroup() != null) {
                groupName = updatedGuests.get(0).getGroup().getName();
                body.append("Grupo: ").append(groupName).append("\n\n");
                log.debug("Procesando actualizaciones para el grupo: {}", groupName);
            }
            
            // Add information about each updated guest
            body.append("Detalles de los invitados:\n");
            for (Guest guest : updatedGuests) {
                log.debug("Procesando datos de invitado: {} {} (ID: {})", 
                        guest.getName(), guest.getSurname(), guest.getId());
                
                body.append("- ").append(guest.getName()).append(" ").append(guest.getSurname()).append(": ");
                
                if (guest.getConfirmedAttendance() == null) {
                    body.append("Pendiente de confirmar");
                    log.debug("Estado: Pendiente de confirmar");
                } else if (guest.getConfirmedAttendance()) {
                    body.append("ASISTIRÁ");
                    log.debug("Estado: ASISTIRÁ");
                    
                    // Add additional details if available
                    if (guest.getDietaryRestrictions() != null && !guest.getDietaryRestrictions().isEmpty()) {
                        body.append(" - Restricciones alimentarias: ").append(guest.getDietaryRestrictions());
                        log.debug("Restricciones alimentarias: {}", guest.getDietaryRestrictions());
                    }
                    
                    if (guest.getBus() != null && !guest.getBus().isEmpty()) {
                        body.append(" - Bus: ").append(guest.getBus());
                        log.debug("Bus: {}", guest.getBus());
                    }
                    
                    if (guest.getSuggests() != null && !guest.getSuggests().isEmpty()) {
                        body.append(" - Sugerencia música: ").append(guest.getSuggests());
                        log.debug("Sugerencias de música: {}", guest.getSuggests());
                    }
                } else {
                    body.append("NO ASISTIRÁ");
                    log.debug("Estado: NO ASISTIRÁ");
                }
                
                body.append("\n");
            }
            
            // Add summary statistics
            body.append("\n\nResumen general:\n");
            body.append("- Total de invitados: ").append(statistics.get("total")).append("\n");
            body.append("- Confirmados: ").append(statistics.get("confirmed")).append("\n");
            body.append("- Rechazados: ").append(statistics.get("declined")).append("\n");
            body.append("- Pendientes: ").append(statistics.get("pending")).append("\n");
            
            message.setText(body.toString());
            
            // Log email content at trace level (for development/debugging only)
            log.trace("Contenido del email: \n{}", body.toString());
            
            // Send the email
            log.info("Enviando notificación por email para el grupo {}", groupName);
            mailSender.send(message);
            log.info("Email enviado correctamente a {}", adminEmail);
        } catch (MailException e) {
            log.error("Error al enviar el correo de notificación: {}", e.getMessage(), e);
            // You might want to re-throw or handle this differently
            // depending on your application's requirements
        } catch (Exception e) {
            log.error("Error inesperado al preparar o enviar el correo: {}", e.getMessage(), e);
        }
    }
    
    private Map<String, Long> getGuestStatistics() {
        log.debug("Obteniendo estadísticas de invitados");
        try {
            long total = guestRepository.count();
            long confirmed = guestRepository.countByConfirmedAttendanceTrue();
            long declined = guestRepository.countByConfirmedAttendanceFalse();
            long pending = guestRepository.countByConfirmedAttendanceIsNull();
            
            log.debug("Estadísticas obtenidas: total={}, confirmados={}, rechazados={}, pendientes={}",
                    total, confirmed, declined, pending);
            
            return Map.of(
                "total", total,
                "confirmed", confirmed,
                "declined", declined,
                "pending", pending
            );
        } catch (Exception e) {
            log.error("Error al obtener estadísticas de invitados: {}", e.getMessage(), e);
            // Provide default values in case of error
            return Map.of(
                "total", 0L,
                "confirmed", 0L,
                "declined", 0L,
                "pending", 0L
            );
        }
    }
}