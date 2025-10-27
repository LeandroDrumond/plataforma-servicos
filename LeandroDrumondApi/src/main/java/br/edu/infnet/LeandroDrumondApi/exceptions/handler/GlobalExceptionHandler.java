package br.edu.infnet.LeandroDrumondApi.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.edu.infnet.LeandroDrumondApi.exceptions.PrestadorNaoEncontradoException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PrestadorNaoEncontradoException.class)
    public ResponseEntity<String> handlePrestadorNaoEncontrado(PrestadorNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
