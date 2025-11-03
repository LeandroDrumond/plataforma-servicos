package br.edu.infnet.LeandroDrumondApi.exceptions.handler;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.*;

import br.edu.infnet.LeandroDrumondApi.exceptions.PrestadorNaoEncontradoException;
import br.edu.infnet.LeandroDrumondApi.exceptions.OrdemNaoEncontradaException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, String>> problem(HttpStatus status, String error, String detail) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.toString());
        body.put("error", error);
        if (detail != null && !detail.isBlank()) body.put("detail", detail);
        return new ResponseEntity<>(body, status);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException e) {
        Map<String, String> erros = e.getConstraintViolations().stream().collect(Collectors.toMap(
                v -> v.getPropertyPath().toString(),
                v -> v.getMessage(),
                (a,b) -> a,
                LinkedHashMap::new
        ));
        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleNotReadable(HttpMessageNotReadableException e) {
        String detail = (e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : "");
        return problem(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido.", "Verifique o JSON enviado. " + detail);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String msg = String.format("Parâmetro '%s' inválido. Esperado: %s.",
                e.getName(), e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "tipo correto");
        return problem(HttpStatus.BAD_REQUEST, "Parâmetro inválido.", msg);
    }

    @ExceptionHandler(PrestadorNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handlePrestadorNotFound(PrestadorNaoEncontradoException e) {
        return problem(HttpStatus.NOT_FOUND, e.getMessage(), "O prestador solicitado não foi encontrado na base de dados!");
    }

    @ExceptionHandler(OrdemNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleOrdemNotFound(OrdemNaoEncontradaException e) {
        return problem(HttpStatus.NOT_FOUND, e.getMessage(), "A ordem de serviço solicitada não foi encontrada.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return problem(HttpStatus.BAD_REQUEST, e.getMessage(), "Algum argumento inválido foi fornecido para essa operação!");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException e) {
        return problem(HttpStatus.CONFLICT, e.getMessage(), "Regra de negócio/fluxo violada.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException e) {
        String raw = (e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : e.getMessage());
        String msgLC = raw != null ? raw.toLowerCase() : "";
        if (msgLC.contains("cpf")) {
            return problem(HttpStatus.CONFLICT,
                    "Já existe um prestador cadastrado com este CPF.",
                    "O CPF informado já está associado a outro prestador no sistema.");
        }
        return problem(HttpStatus.CONFLICT,
                "Problema de integridade de dados (registro duplicado ou dependência inválida).",
                "Verifique se há valores duplicados ou chaves estrangeiras incorretas.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException e) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Ocorreu um erro interno no servidor!");
    }
}
