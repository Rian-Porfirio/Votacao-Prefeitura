package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.dto.EmpresaDto;
import br.com.rianporfirio.sistemavotacao.service.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class EmpresasController {

    private final EmpresaService empresaService;
    private final String defaultRedirect = "redirect:/dashboard/empresas";

    public EmpresasController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/create")
    public String createEmpresa(@ModelAttribute EmpresaDto empresaDto, @RequestParam("foto") MultipartFile foto) {
        try {
            empresaService.create(empresaDto, foto);
        } catch (Exception ex) {
            log.error("não foi possível inserir uma nova empresa no sistema. Motivo: {}", ex.getMessage());
        }
        return defaultRedirect;
    }

    @PostMapping("/update/{id}")
    public String updateEmpresa(@ModelAttribute EmpresaDto empresaDto, @RequestParam("foto") MultipartFile foto, @PathVariable long id) {
        try {
            empresaService.update(empresaDto, id, foto);
        } catch (Exception ex) {
            log.error("não foi possível atualizar a empresa. Motivo: {}", ex.getMessage());
        }
        return defaultRedirect;
    }

    @PostMapping("/delete/{id}")
    public String deleteEmpresa(@PathVariable long id) {
        try {
            empresaService.delete(id);
        } catch (Exception ex) {
            log.error("Não foi possível deletar a empresa. Motivo: {}", ex.getMessage());
        }
        return defaultRedirect;
    }

    @PostMapping("/status/change/{empresaId}")
    public String changeStatus(@PathVariable long empresaId) {
        empresaService.changeStatus(empresaId);
        return defaultRedirect;
    }
}
