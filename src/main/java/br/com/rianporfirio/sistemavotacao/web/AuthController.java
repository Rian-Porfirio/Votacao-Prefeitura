package br.com.rianporfirio.sistemavotacao.web;
import br.com.rianporfirio.sistemavotacao.service.MailSenderService;
import br.com.rianporfirio.sistemavotacao.service.PasswordGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final MailSenderService mailSenderService;
    private final PasswordGenerationService passwordGenerator;

    public AuthController(MailSenderService mailSenderService, PasswordGenerationService passwordGenerator) {
        this.mailSenderService = mailSenderService;
        this.passwordGenerator = passwordGenerator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/generate/password")
    @ResponseBody
    public ResponseEntity<String> generatePassword(@ModelAttribute("matricula") String matricula) {
        String password = passwordGenerator.generatePassword(matricula);
        return ResponseEntity.ok(password);
    }
}
