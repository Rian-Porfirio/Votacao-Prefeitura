package br.com.rianporfirio.sistemavotacao.error;

import br.com.rianporfirio.sistemavotacao.error.exceptions.VoteRegisterException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ExceptionHandler(VoteRegisterException.class)
    public ResponseEntity<Object> handleVotingError(VoteRegisterException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> businessRuleError(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> maxSizeFileUploadError() {
        return ResponseEntity.badRequest().body("Arquivo excede o limite de 2 MB");
    }
}
