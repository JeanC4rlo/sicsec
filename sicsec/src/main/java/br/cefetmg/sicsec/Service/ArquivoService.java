package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.AtividadeRepository;

@Service
public class ArquivoService {
    @Autowired
    AtividadeRepository atividadeRepository;

    private static final long TAMANHO_MAX = 10 * 1024 * 1024;
    private static final List<String> EXTENSOES_PERMITIDAS = List.of(".txt", ".pdf", ".jpg", ".png", ".jpeg", ".docx");

    public void salvarArquivoResposta(Resposta resposta, MultipartFile arquivo) throws IOException {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado.");
        }

        if (!validarArquivo(arquivo)) {
            throw new IllegalArgumentException("O arquivo enviado não é permitido.");
        }

        Path pastaAtividade = Paths.get("uploads/resposta-" + resposta.getId());
        Files.createDirectories(pastaAtividade);

        String nomeOriginal = limparNome(arquivo.getOriginalFilename());
        String nomeFinal = gerarNomeSeguro(pastaAtividade, nomeOriginal);

        Path destino = pastaAtividade.resolve(nomeFinal);
        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        resposta.setNomeArquivo(nomeFinal);
    }

    public void salvarListaArquivos(Atividade atividade, MultipartFile[] arquivos) throws IOException {
        Path pastaAtividade = Paths.get("uploads/atividade-" + atividade.getId());
        Files.createDirectories(pastaAtividade);
        List<String> nomesSalvos = new ArrayList<>();

        for (MultipartFile arquivo : arquivos) {
            if (arquivo != null && !arquivo.isEmpty()) {
                if (!validarArquivo(arquivo)) {
                    continue;
                }
                String nomeOriginal = limparNome(arquivo.getOriginalFilename());
                String nomeFinal = gerarNomeSeguro(pastaAtividade, nomeOriginal);

                Path destino = pastaAtividade.resolve(nomeFinal);

                Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                nomesSalvos.add(nomeFinal);
            }
        }

        atividade.setNomesArquivos(nomesSalvos);
    }

    public boolean validarArquivo(MultipartFile arquivo) throws IOException {
        String nome = arquivo.getOriginalFilename().toLowerCase();
        boolean extensaoValida = EXTENSOES_PERMITIDAS.stream().anyMatch(nome::endsWith);

        if (!extensaoValida)
            return false;
        if (arquivo.getSize() > TAMANHO_MAX)
            return false;

        return true;
    }

    private String limparNome(String nome) {
        return nome.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
    }

    public String gerarNomeSeguro(Path pastaAtividade, String nomeBase) {
        Path destino = pastaAtividade.resolve(nomeBase);

        if (!Files.exists(destino)) {
            return nomeBase;
        }

        String nomeSemExt = nomeBase.contains(".")
                ? nomeBase.substring(0, nomeBase.lastIndexOf('.'))
                : nomeBase;
        String ext = nomeBase.contains(".")
                ? nomeBase.substring(nomeBase.lastIndexOf('.'))
                : "";

        int contador = 1;
        String novoNome;

        do {
            novoNome = nomeSemExt + "_" + contador + ext;
            destino = pastaAtividade.resolve(novoNome);
            contador++;
        } while (Files.exists(destino));

        return novoNome;
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
