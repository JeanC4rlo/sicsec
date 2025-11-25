package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Repository.AtividadeRepository;

@Service
public class ValidarArquivosService {
    @Autowired
    AtividadeRepository atividadeRepository;

    private static final Set<String> EXTENSOES_PERMITIDAS = Set.of(".txt", ".pdf", ".docx", ".zip");
    private static final long TAMANHO_MAX = 5 * 1024 * 1024;

    public Atividade validarListaArquivos(Atividade atividade, MultipartFile[] arquivos) throws IOException {
        if (arquivos != null && arquivos.length > 0) {
            List<MultipartFile> arquivosValidos = new ArrayList<>();
            for (MultipartFile arquivo : arquivos) {
                if (validarArquivoUnico(arquivo)) {
                    arquivosValidos.add(arquivo);
                }
            }
            List<String> nomesArquivos = arquivosValidos.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .toList();

            atividade.setNomesArquivos(nomesArquivos);

        } else {
            atividade.setNomesArquivos(Collections.emptyList());
        }

        return atividadeRepository.save(atividade);
    }

    public boolean validarArquivoUnico(MultipartFile arquivo) throws IOException {
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path temp = Files.createTempFile("upload_", "_" + limparNome(arquivo.getOriginalFilename()));
        Files.write(temp, arquivo.getBytes());

        try {
            String tipoMime = Files.probeContentType(temp);
            if (tipoMime == null)
                tipoMime = "";
            String nomeArquivo = arquivo.getOriginalFilename().toLowerCase();
            boolean extensaoValida = EXTENSOES_PERMITIDAS.stream().anyMatch(nomeArquivo::endsWith);
            if (!extensaoValida || tipoMime.equals("application/x-msdownload")) {
                return false;
            }

            if (Files.size(temp) > TAMANHO_MAX) {
                return false;
            }

            Path destino = uploadDir.resolve(limparNome(arquivo.getOriginalFilename()));
            Files.copy(temp, destino, StandardCopyOption.REPLACE_EXISTING);

            return true;

        } finally {
            Files.deleteIfExists(temp);
        }
    }

    private static String limparNome(String nome) {
        if (nome == null)
            return "arquivo_sem_nome";
        return Paths.get(nome).getFileName().toString().replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
