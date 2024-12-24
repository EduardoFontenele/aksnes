package br.com.gateway.aksnes.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@ToString
public class ApiSecurityException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String message;

}
