package br.com.rianporfirio.sistemavotacao.web;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import br.com.rianporfirio.sistemavotacao.service.MailSenderService;
import br.com.rianporfirio.sistemavotacao.service.PasswordGenerationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {

    private final MailSenderService mailSenderService;
    private final PasswordGenerationService passwordGenerator;
    private final IEmpresaRepository repository;

    public AuthController(MailSenderService mailSenderService, PasswordGenerationService passwordGenerator, IEmpresaRepository repository) {
        this.mailSenderService = mailSenderService;
        this.passwordGenerator = passwordGenerator;
        this.repository = repository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/generate/password")
    @ResponseBody
    public ResponseEntity<String> generatePassword(@ModelAttribute("matricula") String matricula, Model model) {
        try {
            String password = passwordGenerator.generatePassword(matricula);
            return ResponseEntity.ok(password);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("empresas", repository.findAll());
        return "home";
    }
}
