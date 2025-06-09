package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FuncionarioDetailsService implements UserDetailsService {

    private final IFuncionarioRepository funcionarioRepository;
    private final PasswordGenerationService passwordGeneration;

    public FuncionarioDetailsService(IFuncionarioRepository funcionarioRepository, PasswordGenerationService passwordGeneration) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordGeneration = passwordGeneration;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var funcionario = funcionarioRepository.findByMatricula(username);

        if (funcionario == null) {
            log.error("O usuário {} não foi encontrado", username);
            throw new UsernameNotFoundException("Funcionário não encontrado");
        }

        return funcionario;
    }
}
