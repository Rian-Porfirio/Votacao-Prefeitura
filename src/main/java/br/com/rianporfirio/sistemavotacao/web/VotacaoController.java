package br.com.rianporfirio.sistemavotacao.web;

 import br.com.rianporfirio.sistemavotacao.dto.OpcaoDto;
 import br.com.rianporfirio.sistemavotacao.service.OpcaoService;
import br.com.rianporfirio.sistemavotacao.service.VotacaoService;
 import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

 import java.util.List;


@Controller
@RequestMapping("/votacao")
public class VotacaoController {

    private final VotacaoService votacaoService;
    private final OpcaoService opcaoService;

    public VotacaoController(VotacaoService votacaoService, OpcaoService opcaoService) {
        this.votacaoService = votacaoService;
        this.opcaoService = opcaoService;
    }

    @GetMapping
    public String getOpcoes(Model model) {
        var opcoesList = votacaoService.getOpcoes(1);
        model.addAttribute("opcoes", opcoesList);
        return "votacao";
    }

    @GetMapping("/relatorio")
    public String relatorio(Model model) {
        var opcoesList = votacaoService.getOpcoes(1);
        model.addAttribute("empresas", opcoesList);
        return "relatorio";
    }

    @PostMapping("/ativarModoExclusao")
    public String ativarModoExclusao(Model model) {
        model.addAttribute("modoExclusao", true);
        model.addAttribute("opcoes", votacaoService.getOpcoes(1));
        return "votacao";
    }

    @PostMapping("/excluirEmpresas")
    public String excluirEmpresas(@RequestParam List<Long> idsSelecionados) {
        opcaoService.deleteCascade(idsSelecionados);
        return "redirect:/votacao";
    }

    @PostMapping
    public String adicionar(@RequestParam String nome, Model model) {
        opcaoService.create(new OpcaoDto(nome));
        var opcoesList = votacaoService.getOpcoes(1);
        model.addAttribute("opcoes", opcoesList);
        return "votacao";
    }
}
