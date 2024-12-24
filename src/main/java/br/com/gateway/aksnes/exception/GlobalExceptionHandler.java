package br.com.gateway.aksnes.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<String> handle(KafkaException ex) {
        return new ResponseEntity<>("Deu merda no Kafka", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
