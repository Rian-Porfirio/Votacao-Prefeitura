package br.com.rianporfirio.sistemavotacao.web;
import br.com.rianporfirio.sistemavotacao.service.MailSenderService;
import br.com.rianporfirio.sistemavotacao.service.PasswordGenerationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


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
    public void generatePassword(@ModelAttribute("matriculaSenha") String matricula) {
        passwordGenerator.generatePassword(matricula);
    }
}
