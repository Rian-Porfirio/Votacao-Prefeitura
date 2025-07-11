package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FuncionarioService {

    private final IFuncionarioRepository funcionarioRepository;
    private final IEmpresaRepository opcaoRepository;

    public FuncionarioService(IFuncionarioRepository funcionarioRepository, IEmpresaRepository opcaoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.opcaoRepository = opcaoRepository;
    }

    public void inserirVoto(long opcaoId, String matricula) {
        var opcao = opcaoRepository.findById(opcaoId).get();
        var funcionario = funcionarioRepository.findByMatricula(matricula);
        funcionario.votar(opcao);
        funcionarioRepository.save(funcionario);
    }
}
