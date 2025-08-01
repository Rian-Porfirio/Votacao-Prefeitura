package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import br.com.rianporfirio.sistemavotacao.repository.IFuncionarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final IEmpresaRepository empresaRepository;
    private final IFuncionarioRepository funcionarioRepository;

    public HomeController(IEmpresaRepository empresaRepository, IFuncionarioRepository funcionarioRepository) {
        this.empresaRepository = empresaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var funcionario = funcionarioRepository.findByMatricula(userDetails.getUsername());
        var empresas = empresaRepository.findAll().stream()
                .filter(Empresa::isAtivo)
                .toList();

        model.addAttribute("hasVoted", funcionario.hasVoted());
        model.addAttribute("empresas", empresas);
        return "home";
    }
}
