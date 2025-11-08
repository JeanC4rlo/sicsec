package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.dto.HomeAtividadesDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")

public class AtividadeController {
    @Autowired
    private AtividadeRepository atividadesRepository;

    @PostMapping("/salvar")
    public ResponseEntity<Atividade> salvarAtividade(@RequestPart("atividade") Atividade atividade,
            @RequestPart(value = "arquivos", required = false) MultipartFile[] arquivos) {
        if (arquivos != null && arquivos.length > 0) {
            for (MultipartFile file : arquivos) {
                try {
                    ValidacaoDeArquivosHelper.validar(file);
                } catch (IOException e) {
                    return ResponseEntity.badRequest().body(atividade);
                }
            }
            List<String> nomesArquivos = Arrays.stream(arquivos)
                    .map(MultipartFile::getOriginalFilename)
                    .toList();
            atividade.setArquivos(nomesArquivos);
        } else {
            atividade.setArquivos(Collections.emptyList());
        }

        Atividade nova = atividadesRepository.save(atividade);
        return ResponseEntity.ok(nova);
    }

    @GetMapping("/arquivos/{atividadeId}")
    public ResponseEntity<List<String>> listarArquivos(@PathVariable Long atividadeId) {
        return atividadesRepository.findById(atividadeId)
                .map(atividade -> {
                    List<String> arquivos = atividade.getNomesArquivos();
                    if (arquivos == null)
                        arquivos = List.of();
                    return ResponseEntity.ok(arquivos);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{nomeArquivo}")
    public ResponseEntity<Resource> downloadArquivo(@PathVariable String nomeArquivo) throws IOException {
        Path path = Path.of("uploads", nomeArquivo);
        if (!Files.exists(path))
            return ResponseEntity.notFound().build();

        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("/atividades")
    public List<Atividade> Listar() {
        return atividadesRepository.findAll();
    }

    @GetMapping("/home-atividades")
    public List<HomeAtividadesDTO> ListarHomeAtividades() {
        return atividadesRepository.findAll().stream()
                .map(a -> new HomeAtividadesDTO(a.getId(), a.getNome(), a.getTipo(), a.getValor(),
                        a.getDataEncerramento(), a.getHoraEncerramento()))
                .toList();
    }

    @GetMapping("/abrir-atividade/{id}")
    public ResponseEntity<Atividade> buscarAtividade(@PathVariable Long id) {
        return atividadesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
