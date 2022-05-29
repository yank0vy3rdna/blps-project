package ru.itmo.blps;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class, MissingCsrfTokenException.class, InvalidCsrfTokenException.class, SessionAuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException(RuntimeException ex) {
        if (ex instanceof SessionAuthenticationException) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(ex.getMessage());
        }else if (ex instanceof AuthenticationException) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(ex.getMessage());
        } else if (ex instanceof MissingCsrfTokenException) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(ex.getMessage());
        } else if (ex instanceof InvalidCsrfTokenException) {
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Unknown exception : "+ ex.getMessage());
    }
}
