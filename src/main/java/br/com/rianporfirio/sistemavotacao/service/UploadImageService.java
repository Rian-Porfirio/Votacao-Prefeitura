package br.com.rianporfirio.sistemavotacao.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Service
public class UploadImageService {

    private final String logosFolder = "uploads/logos";
    private final Set<String> allowedMimeTypes = Set.of("image/png", "image/jpeg", "image/jpg");

    public String uploadLogo(MultipartFile file, String optionName) throws IOException {
        if (file.isEmpty()) {
            throw new FileUploadException("Arquivo de imagem não inserido");
        }

        if (!allowedMimeTypes.contains(file.getContentType())) {
            throw new InvalidContentTypeException("Arquivo não permitido");
        }

        Path uploadPath = Paths.get(logosFolder, optionName);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = buildFileName(file.getOriginalFilename());
        Path completePath = uploadPath.resolve(filename);
        file.transferTo(completePath);

        return "/logos/" + optionName + "/" + filename;
    }

    public void deleteDirectory(String optionName) throws IOException {
        Path deletePath = Paths.get(logosFolder, optionName);

        if (!Files.exists(deletePath)) {
            log.debug("Diretório '{}' não encontrado", deletePath);
            return;
        }

        deleteAllFiles(deletePath);

        try {
            Files.delete(deletePath);
            log.info("Diretório '{}' deletado com sucesso.", deletePath);
        } catch (IOException e) {
            log.error("Erro ao deletar o diretório '{}': {}", deletePath, e.getMessage(), e);
            throw new IOException("Erro ao deletar o diretório.", e);
        }
    }

    private void deleteAllFiles(Path deletePath) throws IOException{
        try (Stream<Path> files = Files.list(deletePath)) {
            var fileList = files.toList();

            for (Path file : fileList) {
                try {
                    Files.delete(file);
                    log.debug("Arquivo deletado {}", file.getFileName());
                } catch (Exception ex) {
                    log.error("Não foi possivel deletar o arquivo {}", file.getFileName());
                }
            }
        }
    }

    private String currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    private String buildFileName(String filename) {
        return currentUser() + "_" + System.currentTimeMillis() + filename;
    }
}


