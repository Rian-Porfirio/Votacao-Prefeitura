package br.com.rianporfirio.sistemavotacao.error;

import br.com.rianporfirio.sistemavotacao.error.exceptions.VoteAlreadyRegistered;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(VoteAlreadyRegistered.class)
    public ResponseEntity<Object> handleVotingError(VoteAlreadyRegistered ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
