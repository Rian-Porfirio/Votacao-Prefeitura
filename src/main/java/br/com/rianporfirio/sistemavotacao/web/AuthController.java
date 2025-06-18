package br.com.rianporfirio.sistemavotacao.web;
import br.com.rianporfirio.sistemavotacao.service.MailSenderService;
import br.com.rianporfirio.sistemavotacao.service.PasswordGenerationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    private final MailSenderService mailSenderService;
    private final PasswordGenerationService passwordGenerationService;

    public AuthController(MailSenderService mailSenderService, PasswordGenerationService passwordGenerationService) {
        this.mailSenderService = mailSenderService;
        this.passwordGenerationService = passwordGenerationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/generate-password")
    public String generatePassword() {
        String message = "Aqui está sua senha: " + passwordGenerationService.randomPassword();

        return "login";
    }
}
