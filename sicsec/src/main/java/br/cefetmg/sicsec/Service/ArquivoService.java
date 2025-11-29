package br.cefetmg.sicsec.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Repository.AtividadeRepository;

@Service
public class ArquivoService {

    @Autowired
    AtividadeRepository atividadeRepository;

    private final Path uploadDir = Path.of("uploads");

    public Resource carregarArquivo(String nomeArquivo) throws IOException {
        Path path = uploadDir.resolve(nomeArquivo);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("Arquivo não encontrado: " + nomeArquivo);
        }
        return new UrlResource(path.toUri());
    }

    public List<?> listarArquivos(Long atividadeId) {
        return atividadeRepository.findById(atividadeId)
                .map(atividade -> {
                    List<String> arquivos = atividade.getNomesArquivos();
                    return arquivos != null ? arquivos : List.of();
                })
                .orElseGet(() -> {
                    return List.of();
                });
    }

}
