package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Arquivo;
import br.cefetmg.sicsec.Model.FileOwner;
import br.cefetmg.sicsec.Model.FileOwnerTypes;
import br.cefetmg.sicsec.Repository.ArquivoRepository;
import br.cefetmg.sicsec.Repository.AtividadeRepository;

@Service
public class ArquivoService {

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    private static final long TAMANHO_MAX = 10 * 1024 * 1024;
    private static final List<String> EXTENSOES_PERMITIDAS = List.of(".txt", ".pdf", ".jpg", ".png", ".jpeg", ".docx");

    public Arquivo getArquivo(Long arquivoId) {
        return arquivoRepository.findById(arquivoId).get();
    }

    private void popularArquivoEntidade(Arquivo arquivoEntidade, Long tamanho, String nomeOriginal,
            String nomeFinal, FileOwnerTypes tipoDonoArquivo, long donoId) throws IOException {

        arquivoEntidade.setTipoDonoArquivo(tipoDonoArquivo);
        arquivoEntidade.setDonoId(donoId);
        arquivoEntidade.setNomeOriginal(nomeOriginal);
        arquivoEntidade.setNomeSalvo(nomeFinal);
        arquivoEntidade.setTamanho(tamanho);
        arquivoEntidade.setDataUpload(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
    }

    public Arquivo salvarArquivo(FileOwner donoArquivo, MultipartFile arquivo) throws IOException {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado.");
        }

        if (!validarArquivo(arquivo)) {
            throw new IllegalArgumentException("O arquivo enviado não é permitido.");
        }

        Path pastaDono = Paths.get(
                "uploads/" + donoArquivo.getTipoDonoArquivo().toString().toLowerCase() + "-" + donoArquivo.getId());
        Files.createDirectories(pastaDono);

        String nomeOriginal = limparNome(arquivo.getOriginalFilename());
        String nomeFinal = gerarNomeSeguro(pastaDono, nomeOriginal);

        Path destino = pastaDono.resolve(nomeFinal);
        Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        Arquivo arquivoEntidade = new Arquivo();
        popularArquivoEntidade(arquivoEntidade, arquivo.getSize(), nomeOriginal, nomeFinal,
                donoArquivo.getTipoDonoArquivo(), donoArquivo.getId());

        return arquivoRepository.save(arquivoEntidade);
    }

    public void salvarListaArquivos(FileOwner donoArquivo, MultipartFile[] arquivos) throws IOException {
        Path pastaDono = Paths.get(
                "uploads/" + donoArquivo.getTipoDonoArquivo().toString().toLowerCase() + "-" + donoArquivo.getId());
        Files.createDirectories(pastaDono);
        List<Arquivo> listaArquivos = new ArrayList<>();

        for (MultipartFile arquivo : arquivos) {
            if (arquivo == null || arquivo.isEmpty())
                continue;
            if (!validarArquivo(arquivo))
                continue;

            String nomeOriginal = limparNome(arquivo.getOriginalFilename());
            String nomeFinal = gerarNomeSeguro(pastaDono, nomeOriginal);

            Path destino = pastaDono.resolve(nomeFinal);

            Arquivo arquivoEntidade = new Arquivo();
            popularArquivoEntidade(arquivoEntidade, arquivo.getSize(), nomeOriginal, nomeFinal,
                    donoArquivo.getTipoDonoArquivo(), donoArquivo.getId());

            Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            listaArquivos.add(arquivoEntidade);
        }
        arquivoRepository.saveAll(listaArquivos);
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
        String limpo = nome.trim()
                .replaceAll("[\\\\/]", "_")
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        if (limpo.equals(".") || limpo.equals("..")) {
            limpo = "_" + limpo + "_";
        }

        return limpo;
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

    public List<Arquivo> listarArquivos(String tipoDonoArquivo, Long donoId) {
        FileOwnerTypes tipo = FileOwnerTypes.valueOf(tipoDonoArquivo.toUpperCase());
        return arquivoRepository.findByTipoDonoArquivoAndDonoId(tipo, donoId);
    }
}
