package br.com.rianporfirio.sistemavotacao.service;

import br.com.rianporfirio.sistemavotacao.utils.AuthUtils;
import br.com.rianporfirio.sistemavotacao.utils.NameUtils;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
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

    public String uploadLogo(MultipartFile file, String name) throws IOException {
        if (file.isEmpty()) {
            throw new ValidationException("Arquivo não enviado");
        }

        if (!allowedMimeTypes.contains(file.getContentType())) {
            throw new ValidationException("Arquivo Não Suportado. Por favor, Envie Apenas PNG, JPG e JPEG .");
        }

        String folderName = NameUtils.removeSpaces(name);
        Path uploadPath = Paths.get(logosFolder, folderName);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = buildFileName(file.getOriginalFilename());
        Path completePath = uploadPath.resolve(filename);
        file.transferTo(completePath);

        return pathToSave(folderName, filename);
    }

    public void deleteDirectory(String name) throws IOException {
        Path deletePath = Paths.get(logosFolder, NameUtils.removeSpaces(name));

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

    public String updateFolderName(String originalPath, String name) throws IOException {
        String relativePath = originalPath.replaceFirst("^/logos/", ""); // remove /logos/ do início da ‘String’.
        Path oldUploadPath = Paths.get(logosFolder, relativePath).getParent();
        Path newUploadPath = Paths.get(logosFolder, NameUtils.removeSpaces(name));

        if (!Files.exists(oldUploadPath)) {
            throw new ValidationException("Erro ao renomear diretório de arquivos");
        }

        Files.move(oldUploadPath, newUploadPath);
        String filename = Paths.get(originalPath).getFileName().toString();
        return pathToSave(NameUtils.removeSpaces(name), filename);
    }

    private String buildFileName(String filename) {
        return AuthUtils.currentUsername() + "_" + System.currentTimeMillis() + filename;
    }

    private String pathToSave(String empresa, String filename) {
        return "/logos/" + empresa + "/" + filename;
    }
}
