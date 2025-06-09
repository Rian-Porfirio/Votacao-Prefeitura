package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.dto.OpcaoDto;
import br.com.rianporfirio.sistemavotacao.service.FuncionarioService;
import br.com.rianporfirio.sistemavotacao.service.OpcaoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class OpcoesController {

    private final OpcaoService opcaoService;
    private final FuncionarioService funcionarioService;
    private final String defaultRedirect = "redirect:/";

    public OpcoesController(OpcaoService opcaoService, FuncionarioService funcionarioService) {
        this.opcaoService = opcaoService;
        this.funcionarioService = funcionarioService;
    }

    @GetMapping()
    public String listOptions(Model model) {
        model.addAttribute("opcoes", opcaoService.getAll());
        return "votacao";
    }

    @PostMapping("create")
    public String createOption(@ModelAttribute("nome") String nome, @ModelAttribute("foto") MultipartFile foto) {
        opcaoService.create(new OpcaoDto(nome));
        return defaultRedirect;
    }

    @PostMapping("update/{id}")
    public String updateOption(@ModelAttribute("nome") String nome, @ModelAttribute("foto") MultipartFile foto, @PathVariable long id) {
        opcaoService.update(new OpcaoDto(nome), id);
        return defaultRedirect;
    }

    @PostMapping("delete/{id}")
    public String deleteOption(@PathVariable long id) {
        opcaoService.delete(id);
        return defaultRedirect;
    }

    @PostMapping("votar/{opcaoId}")
    public String realizarVoto(@PathVariable long opcaoId, @AuthenticationPrincipal UserDetails userDetails) {
        funcionarioService.inserirVoto(opcaoId, userDetails.getUsername());
        return defaultRedirect;
    }
}
