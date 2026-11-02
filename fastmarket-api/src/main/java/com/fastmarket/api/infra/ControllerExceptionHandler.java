package com.fastmarket.api.infra;

import com.fastmarket.api.infra.exceptions.ValorInsuficienteException;
import com.fastmarket.api.infra.exceptions.QuantidadeInsuficienteException;
import com.fastmarket.api.dtos.exceptions.FieldsErrorsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threatEntityNotFound(EntityNotFoundException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDataIntegryViolation(DataIntegrityViolationException exception){
        return ResponseEntity.badRequest().body("Valor único duplicado.");
    }
    @ExceptionHandler(ValorInsuficienteException.class)
    public ResponseEntity threatInsufficientAmount(ValorInsuficienteException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity threatConstraintViolationException(ConstraintViolationException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(QuantidadeInsuficienteException.class)
    public ResponseEntity threatInsufficientQuantity(QuantidadeInsuficienteException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity threatMethodArgumentNotValid(MethodArgumentNotValidException exception){
        List<FieldError> errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(FieldsErrorsException::new).toList());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception exception){
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
