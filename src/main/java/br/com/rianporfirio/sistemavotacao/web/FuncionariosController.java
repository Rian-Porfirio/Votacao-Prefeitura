package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.service.FuncionarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FuncionariosController {

    private final FuncionarioService funcionarioService;
    private final String defaultRedirect = "redirect:/dashboard/empresas";

    public FuncionariosController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("votar/{empresaId}")
    public String vote(@PathVariable long empresaId, @AuthenticationPrincipal UserDetails userDetails) {
        funcionarioService.inserirVoto(empresaId, userDetails.getUsername());
        return defaultRedirect;
    }
}
