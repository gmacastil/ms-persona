package com.lite.ms_persona.infraestructure.api;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex, HttpServletRequest request) {
        return handle(ex, request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return handle(ex, request, HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<String> handleBadRequest(Exception ex, HttpServletRequest request) {
        String message = ex instanceof IllegalArgumentException
                ? ex.getMessage()
                : "El cuerpo o los parámetros de la solicitud no son válidos";
        return handle(ex, request, HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpected(Exception ex, HttpServletRequest request) {
        if (ex instanceof ErrorResponse errorResponse) {
            return handle(ex, request, errorResponse.getStatusCode(), "No se pudo procesar la solicitud");
        }
        return handle(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno");
    }

    private ResponseEntity<String> handle(
            Exception ex,
            HttpServletRequest request,
            HttpStatusCode status,
            String message) {
        Controller.logError(request.getMethod(), request.getRequestURI(), status, ex);
        return ResponseEntity.status(status).body(message);
    }
}
