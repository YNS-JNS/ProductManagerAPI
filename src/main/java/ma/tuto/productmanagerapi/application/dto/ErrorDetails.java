package ma.tuto.productmanagerapi.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

// *** DTO pour les réponses d'erreur *** :

@Data
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private int statusCode;

    public ErrorDetails(String message, String details, int statusCode) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
        this.statusCode = statusCode;
    }
}
