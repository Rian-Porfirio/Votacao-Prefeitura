package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.dto.VotoDto;
import br.com.rianporfirio.sistemavotacao.error.exceptions.VoteAlreadyRegistered;
import br.com.rianporfirio.sistemavotacao.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FuncionariosController {

    private final FuncionarioService funcionarioService;
    private final String defaultRedirect = "redirect:/";

    public FuncionariosController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/votar")
    public ResponseEntity<String> vote(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute VotoDto dto) throws Exception {
        funcionarioService.inserirVoto(dto.empresaId(), userDetails.getUsername());
        return ResponseEntity.ok("Voto registrado com sucesso");
    }
}
