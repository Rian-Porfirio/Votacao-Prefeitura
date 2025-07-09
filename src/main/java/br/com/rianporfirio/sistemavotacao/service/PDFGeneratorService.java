package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.domain.Empresa;
import br.com.rianporfirio.sistemavotacao.repository.IEmpresaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Slf4j
@Service
public class PDFGeneratorService {

    private final IEmpresaRepository repository;

    public PDFGeneratorService(IEmpresaRepository repository) {
        this.repository = repository;
    }

    private String parseThymeleafTemplate(List<Empresa> opcoes) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("opcoes", opcoes);

        return templateEngine.process("pdf_template", context);
    }

    private byte[] generatePdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();
        } catch (Exception ex) {
            log.error("Erro ao gerar PDF: {}", ex.getMessage());

            return new byte[0];
        }
    }

    public byte[] exportAll() {
        var opcoes = repository.findAll();
        String html = parseThymeleafTemplate(opcoes);

        return generatePdf(html);
    }

    public byte[] exportUnique(long empresaId) {
        var opcao = repository.findById(empresaId).stream().toList();
        String html = parseThymeleafTemplate(opcao);

        return generatePdf(html);
    }

}
