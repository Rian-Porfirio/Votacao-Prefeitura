package br.com.rianporfirio.sistemavotacao.error.exceptions;

public class VoteAlreadyRegistered extends RuntimeException {
    public VoteAlreadyRegistered(String errorMessage) {
        super(errorMessage);
    }
}
