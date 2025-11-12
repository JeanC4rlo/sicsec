package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Service.ArquivoService;
import br.cefetmg.sicsec.Service.ValidarArquivosService;
import br.cefetmg.sicsec.dto.HomeAtividadesDTO;

@RestController
@RequestMapping("/api/atividade")
@CrossOrigin(origins = "*")

public class AtividadeController {
    @Autowired
    private AtividadeRepository atividadesRepository;

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private ValidarArquivosService validarArquivosService;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @PostMapping("/salvar")
    public ResponseEntity<?> salvarAtividade(
            @RequestPart("atividade") Atividade atividade,
            @RequestPart(value = "arquivos", required = false) MultipartFile[] arquivos) {

        try {
            Atividade nova = validarArquivosService.validarListaArquivos(atividade, arquivos);
            return ResponseEntity.status(HttpStatus.CREATED).body(nova);
        } catch (IOException e) {
            return globalExceptionHandler.handleGeneric(e);
        }
    }

    @GetMapping("/{atividadeId}/arquivos")
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
    public ResponseEntity<?> downloadArquivo(@PathVariable String nomeArquivo) throws IOException {
        try {
            Resource resource = arquivoService.carregarArquivo(nomeArquivo);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return globalExceptionHandler.handleGeneric(e);
        }
    }

    @GetMapping("/atividades")
    public List<Atividade> ListarAtividades() {
        return atividadesRepository.findAll();
    }

    @GetMapping("/home-atividades")
    public List<HomeAtividadesDTO> ListarHomeAtividades() {
        return atividadesRepository.findAll().stream()
                .map(a -> new HomeAtividadesDTO(a.getId(), a.getNome(), a.getTipo(), a.getValor(),
                        a.getDataEncerramento(), a.getHoraEncerramento()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> buscarAtividade(@PathVariable Long id) {
        return atividadesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
