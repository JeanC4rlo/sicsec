package br.cefetmg.sicsec.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.cefetmg.sicsec.Exceptions.CorrecaoException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(CorrecaoException.class)
    public ResponseEntity<?> handleCorrecao(CorrecaoException e) {
        return ResponseEntity.badRequest()
                .body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erro", "Erro inesperado", "detalhes", e.getMessage()));
    }
}
