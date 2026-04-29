package com.ecommerce.priceapi.infrastructure.adapter.in.rest;

import com.ecommerce.priceapi.domain.exception.PriceNotFoundException;
import com.ecommerce.priceapi.infrastructure.adapter.in.rest.response.ErrorResponseDto;
import com.ecommerce.priceapi.infrastructure.adapter.in.rest.response.ValidationErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class PriceControllerAdvice {
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePriceNotFound(PriceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        exception.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleBindException(BindException exception) {
        List<String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .distinct()
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Request validation failed",
                        Instant.now(),
                        details
                ));
    }

    private String formatFieldError(FieldError fieldError) {
        if (fieldError.isBindingFailure()) {
            return "Invalid value for parameter '%s'".formatted(fieldError.getField());
        }
        return "Parameter '%s' %s".formatted(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
