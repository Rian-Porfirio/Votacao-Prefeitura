package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.domain.Funcionario;
import br.com.rianporfirio.sistemavotacao.dto.EmpresaDto;
import br.com.rianporfirio.sistemavotacao.dto.FuncionarioInfoDto;
import br.com.rianporfirio.sistemavotacao.dto.RankingVotosDto;
import br.com.rianporfirio.sistemavotacao.dto.UsuariosDto;
import br.com.rianporfirio.sistemavotacao.repository.IVotoRepository;
import br.com.rianporfirio.sistemavotacao.service.EmpresaService;
import br.com.rianporfirio.sistemavotacao.service.FuncionarioService;
import br.com.rianporfirio.sistemavotacao.service.RankingEmpresaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
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
    private final FuncionarioService funcionarioService;
    private final RankingEmpresaService rankingEmpresaService;
    private final IVotoRepository repository;

    public DashboardController(EmpresaService empresaService,
                               FuncionarioService funcionarioService,
                               RankingEmpresaService rankingEmpresaService,
                               IVotoRepository repository) {
        this.empresaService = empresaService;
        this.funcionarioService = funcionarioService;
        this.rankingEmpresaService = rankingEmpresaService;
        this.repository = repository;
    }

    @GetMapping("/**")
    public String defaultRedirect() {
        return "redirect:/dashboard/empresas";
    }

    @GetMapping("/empresas")
    public String enterprises(Model model, HttpServletRequest req, @RequestParam(value = "search", required = false) String search) {
        getCurrentPath(model, req);

        List<Empresa> empresas = (search == null || search.isBlank()) ? empresaService.getAll() : empresaService.listByName(search);
        List<EmpresaDto> dtos = empresas.stream().map(EmpresaDto::new).toList();

        model.addAttribute("empresas", dtos);
        model.addAttribute("search", search);

        return fragmentPath + "/empresas";
    }

    @GetMapping("/usuarios")
    public String users(Model model, HttpServletRequest req,
                        @RequestParam(value = "search", required = false) String search,
                        @RequestParam(value = "status", required = false) String status,
                        @RequestParam(value = "page", defaultValue = "0") int page) {
        getCurrentPath(model, req);

        Page<Funcionario> funcionarios = funcionarioService.loadPages(search, status, page);
        List<UsuariosDto> usersDto = funcionarios.getContent().stream().map(UsuariosDto::new).toList();

        model.addAttribute("usuarios", funcionarios.getContent());
        model.addAttribute("usersDto", usersDto);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", funcionarios.getTotalPages());
        model.addAttribute("status", status);
        model.addAttribute("search", search);

        FuncionarioInfoDto dto = funcionarioService.loadInformation();
        model.addAttribute("usuariosInfo", dto);

        return fragmentPath + "/usuarios";
    }

    @GetMapping("/votacao")
    public String votation(Model model, HttpServletRequest req,
                           @RequestParam(value = "search", required = false) String search,
                           @RequestParam(value = "status", required = false) String status,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "60") int size) {
        getCurrentPath(model, req);

        model.addAttribute("search", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("status", status);
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        var votos = repository.findAll();
        model.addAttribute("votos", votos);

        return fragmentPath + "/votacao";
    }

    @GetMapping("/relatorios")
    public String relatorio(Model model, HttpServletRequest req,
                           @RequestParam(value = "search", required = false) String search,
                           @RequestParam(value = "status", required = false) String status,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "60") int size) {
        getCurrentPath(model, req);

        List<Empresa> empresas = (search == null || search.isBlank()) ? empresaService.getAll() : empresaService.listByName(search);
        List<EmpresaDto> dtos = empresas.stream().map(EmpresaDto::new).toList();
        model.addAttribute("empresas", dtos);

        RankingVotosDto ranking = rankingEmpresaService.getRanking();

        model.addAttribute("empresasRanking", ranking.empresa().keySet());
        model.addAttribute("votosRanking", ranking.empresa().values());
        model.addAttribute("empresasTopFive", ranking.empresa().keySet().stream().limit(5).toList());
        model.addAttribute("dadosTopFive", ranking.empresa().values().stream().limit(5).toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("status", status);
        model.addAttribute("search", search);
        model.addAttribute("size", size);

        FuncionarioInfoDto dto = funcionarioService.loadInformation();
        model.addAttribute("usuariosInfo", dto);

        return fragmentPath + "/relatorios";
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
