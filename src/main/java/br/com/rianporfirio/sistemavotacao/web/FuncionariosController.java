package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.dto.VotoDto;
import br.com.rianporfirio.sistemavotacao.service.FuncionarioService;
import br.com.rianporfirio.sistemavotacao.utils.AuthUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> vote(@Valid @ModelAttribute VotoDto dto) {
        funcionarioService.inserirVoto(dto.empresaId(), AuthUtils.currentUsername());
        return ResponseEntity.ok("Voto registrado com sucesso");
    }
}
