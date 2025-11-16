package br.cefetmg.sicsec.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class ArquivoService {

    private final Path uploadDir = Path.of("uploads");

    public Resource carregarArquivo(String nomeArquivo) throws IOException {
        Path path = uploadDir.resolve(nomeArquivo);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
        }
        return new UrlResource(path.toUri());
    }
}
