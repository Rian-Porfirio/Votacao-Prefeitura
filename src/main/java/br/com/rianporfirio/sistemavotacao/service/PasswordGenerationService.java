package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordGenerationService {

    private final IFuncionarioRepository funcionarioRepository;

    public PasswordGenerationService(IFuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public String randomPassword() {
        int randomInteger = new Random().nextInt(999999);

        return "testeSenha" + String.format("%06d", randomInteger);
    }
}
