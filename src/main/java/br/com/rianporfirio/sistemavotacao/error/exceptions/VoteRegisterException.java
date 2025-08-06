package br.com.rianporfirio.sistemavotacao.error.exceptions;

public class VoteRegisterException extends RuntimeException {
    public VoteRegisterException(String errorMessage) {
        super(errorMessage);
    }
}
