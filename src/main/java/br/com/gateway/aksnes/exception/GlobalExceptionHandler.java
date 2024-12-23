package br.com.gateway.aksnes.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ApiSecurityException.class)
    public ResponseEntity<String> handle(ApiSecurityException exception) {
        return new ResponseEntity<>( "ASFDCSJNDSC", HttpStatus.UNAUTHORIZED);
    }
}
