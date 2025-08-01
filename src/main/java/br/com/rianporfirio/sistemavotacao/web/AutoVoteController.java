package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.service.AutoVoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AutoVoteController {

    private final AutoVoteService service;

    public AutoVoteController(AutoVoteService service) {
        this.service = service;
    }

    @PostMapping("/gerarVotos")
    public String autoVote() {
        service.autoVote();
        return "redirect:/dashboard/relatorios";
    }
}
