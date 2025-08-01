package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
public class PasswordGenerationService {

    private final String chars = "ABCDEFGHIJKLMNOPQRZTUVWXYZ";
    private final SecureRandom secRandom = new SecureRandom();

    private final IFuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordGenerationService(IFuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String generatePassword(String matricula) {
        Funcionario funcionario = checkPermission(matricula);
        
        String rawPassword = randomPassword();
        log.info("A senha gerada é: {}", rawPassword);

        String encodedPassword = encodePassword(rawPassword);
        setEncodedPassword(funcionario, encodedPassword);

        return rawPassword;
    }

    private String randomPassword() {
        StringBuilder password = new StringBuilder();
        String combinedChars = chars + chars.toLowerCase();

        for (int i = 0; i < 9; i++) {
            int randomInt = secRandom.nextInt(combinedChars.length());
            char randomChar = combinedChars.charAt(randomInt);
            password.append(randomChar);
        }

        return password.toString();
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private void setEncodedPassword(Funcionario funcionario, String encodedPassword) {
        funcionario.setSenha(encodedPassword);
        funcionarioRepository.save(funcionario);
    }

    private Funcionario checkPermission(String matricula) {
        var funcionario = funcionarioRepository.findByMatricula(matricula);
        if (funcionario.getVinculo().equalsIgnoreCase("estagiário")) {
            throw new ValidationException("Usuário não permitido");
        }
        return funcionario;
    }
}
