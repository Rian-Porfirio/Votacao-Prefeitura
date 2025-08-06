package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.dto.EmpresaFormDto;
import br.com.rianporfirio.sistemavotacao.service.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
public class EmpresasController {

    private final EmpresaService empresaService;
    private final String defaultRedirect = "redirect:/dashboard/empresas";

    public EmpresasController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEmpresa(@ModelAttribute EmpresaFormDto empresaFormDto, @RequestParam("foto") MultipartFile foto) throws IOException {
        try {
            empresaService.create(empresaFormDto, foto);
            return ResponseEntity.ok("Empresa Registrada Com Sucesso");
        } catch (Exception ex) {
            log.error("não foi possível inserir uma nova empresa no sistema. Motivo: {}", ex.getMessage());
            throw ex;
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateEmpresa(@ModelAttribute EmpresaFormDto empresaFormDto, @RequestParam("foto") MultipartFile foto, @ModelAttribute("empresaId") long empresaId) throws IOException {
        try {
            empresaService.update(empresaFormDto, empresaId, foto);
            return ResponseEntity.ok("Empresa Atualizada Com Sucesso");
        } catch (Exception ex) {
            log.error("não foi possível atualizar a empresa. Motivo: {}", ex.getMessage());
            throw ex;
        }
    }

    @PostMapping("/delete")
    public String deleteEmpresa(@ModelAttribute("empresaId") long empresaId) {
        try {
            empresaService.delete(empresaId);
        } catch (Exception ex) {
            log.error("Não foi possível deletar a empresa. Motivo: {}", ex.getMessage());
        }
        return defaultRedirect;
    }

    @PostMapping("/status/change")
    public String changeStatus(@ModelAttribute("changeStatus") long empresaId) {
        empresaService.changeStatus(empresaId);
        return defaultRedirect;
    }
}
