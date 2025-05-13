package auth.test.global.exception.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<String> handleResponseStatusExceptions(ResponseStatusException e) {

        return ResponseEntity
                .status(e.getStatusCode())
                .body(e.getReason());
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<String> handleAuthException(AuthenticationException e) {
        HttpStatus statusCode = e instanceof BadCredentialsException
                ? HttpStatus.FORBIDDEN
                : HttpStatus.UNAUTHORIZED;

        return ResponseEntity
                .status(statusCode)
                .body(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    protected ResponseEntity<String> handleAuthorizationDeniedException(AuthorizationDeniedException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<String> handleJwtException(JwtException e) {
        HttpStatus httpStatus = e instanceof ExpiredJwtException ? HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN;

        return ResponseEntity
                .status(httpStatus)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleOtherExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
