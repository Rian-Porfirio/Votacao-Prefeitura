package br.com.rianporfirio.sistemavotacao.error.exceptions;

public class VoteAlreadyRegisteredException extends RuntimeException {
    public VoteAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }
}
