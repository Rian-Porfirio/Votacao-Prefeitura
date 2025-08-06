package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.service.VoteGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoteGenerationController {

    private final VoteGenerationService service;

    public VoteGenerationController(VoteGenerationService service) {
        this.service = service;
    }

    @PostMapping("/gerarVotos")
    public ResponseEntity<String> autoVote() throws Exception {
        service.generateVotes();
        return ResponseEntity.ok("Votos registrados com sucesso");
    }

}
