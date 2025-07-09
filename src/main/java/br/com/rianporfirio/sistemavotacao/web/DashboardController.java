package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.service.EmpresaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final String fragmentPath = "fragments/admin";
    private final EmpresaService empresaService;

    public DashboardController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/empresas")
    public String enterprises(Model model, HttpServletRequest req, @RequestParam(value = "search", required = false) String search) {
        getCurrentPath(model, req);
        List<Empresa> empresas =
                (search == null || search.isBlank()) ? empresaService.getAll() : empresaService.listByName(search);
        model.addAttribute("empresas", empresas);
        model.addAttribute("search", search);
        return fragmentPath + "/empresas";
    }

    @GetMapping("/usuarios")
    public String users(Model model, HttpServletRequest req) {
        getCurrentPath(model, req);
        return fragmentPath + "/usuarios";
    }

    @GetMapping("/atividades")
    public String activity(Model model, HttpServletRequest req) {
        getCurrentPath(model, req);
        return fragmentPath + "/atividades";
    }

    @GetMapping("/votacao")
    public String votation(Model model, HttpServletRequest req) {
        getCurrentPath(model, req);
        return fragmentPath + "/votacao";
    }

    @GetMapping("/ajuda")
    public String help(Model model, HttpServletRequest req) {
        getCurrentPath(model, req);
        return fragmentPath + "/ajuda";
    }

    private void getCurrentPath(Model model, HttpServletRequest req) {
        model.addAttribute("currentPath", req.getRequestURI());
    }
}
