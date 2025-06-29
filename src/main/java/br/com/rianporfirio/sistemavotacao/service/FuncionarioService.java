package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import br.com.rianporfirio.sistemavotacao.repository.IOpcaoRepository;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final IFuncionarioRepository funcionarioRepository;
    private final IOpcaoRepository opcaoRepository;

    public FuncionarioService(IFuncionarioRepository funcionarioRepository, IOpcaoRepository opcaoRepository) {
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
