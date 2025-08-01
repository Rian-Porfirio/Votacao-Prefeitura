package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationService implements UserDetailsService {

    private final IFuncionarioRepository funcionarioRepository;

    public AuthenticationService(IFuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var funcionario = funcionarioRepository.findByMatricula(username);

        if (funcionario == null) {
            log.error("O usuário {} não foi encontrado", username);
            throw new UsernameNotFoundException("Funcionário " + username + " não encontrado");
        }

        return funcionario;
    }
}
