package br.cefetmg.sicsec.Controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.cefetmg.sicsec.Exceptions.CorrecaoException;
import br.cefetmg.sicsec.dto.ApiErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiErrorDTO> handleNotFound(
                        EntityNotFoundException ex,
                        HttpServletRequest request) {

                log.warn("[NOT_FOUND] {}", ex.getMessage());

                ApiErrorDTO error = new ApiErrorDTO(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.name(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(CorrecaoException.class)
        public ResponseEntity<ApiErrorDTO> handleCorrecao(
                        CorrecaoException ex,
                        HttpServletRequest request) {

                log.warn("[CORRECAO] {}", ex.getMessage());

                ApiErrorDTO error = new ApiErrorDTO(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.name(),
                                ex.getMessage(),
                                request.getRequestURI());

                return ResponseEntity.badRequest().body(error);
        }

        @ExceptionHandler(IOException.class)
        public ResponseEntity<ApiErrorDTO> handleIOException(
                        IOException ex,
                        HttpServletRequest request) {

                log.error("[IO_ERROR] {}", ex.getMessage());

                ApiErrorDTO error = new ApiErrorDTO(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.name(),
                                "Erro ao processar arquivos",
                                request.getRequestURI());

                return ResponseEntity.badRequest().body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorDTO> handleGeneric(
                        Exception ex,
                        HttpServletRequest request) {

                log.error("[INTERNAL_ERROR] {}", ex.getMessage());
                log.debug("Stacktrace:", ex);

                ApiErrorDTO error = new ApiErrorDTO(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                "Erro interno inesperado",
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }
}
