package br.com.rianporfirio.sistemavotacao.web;

import br.com.rianporfirio.sistemavotacao.service.OpcaoService;
import br.com.rianporfirio.sistemavotacao.service.PDFGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {

    private final OpcaoService opcaoService;
    private final PDFGeneratorService pdfGeneratorService;

    public RelatorioController(OpcaoService opcaoService, PDFGeneratorService pdfGeneratorService) {
        this.opcaoService = opcaoService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping()
    public String relatorio(Model model) {
        model.addAttribute("opcoes", opcaoService.getAll());

        return "relatorio";
    }

    @GetMapping("/geral")
    public ResponseEntity<byte[]> gerarRelatorioGeral() {
        byte[] pdfBytes = pdfGeneratorService.exportAll();
        return getExportPdfResponse(pdfBytes);
    }

    @GetMapping("/unico/{optionId}")
    public ResponseEntity<byte[]> gerarRelatorioUnico(@PathVariable long optionId) {
        byte[] pdfBytes = pdfGeneratorService.exportUnique(optionId);
        return getExportPdfResponse(pdfBytes);
    }

    @GetMapping("/template")
    public String template(Model model) {
        model.addAttribute("opcoes", opcaoService.getAll());
        return "pdf_template";
    }

    private ResponseEntity<byte[]> getExportPdfResponse(byte[] pdfBytes) {
        if (pdfBytes.length == 0) {
            return ResponseEntity.internalServerError().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
