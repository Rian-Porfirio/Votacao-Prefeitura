package br.com.rianporfirio.sistemavotacao.error;

import br.com.rianporfirio.sistemavotacao.error.exceptions.VoteAlreadyRegisteredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(VoteAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleVotingError(VoteAlreadyRegisteredException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
