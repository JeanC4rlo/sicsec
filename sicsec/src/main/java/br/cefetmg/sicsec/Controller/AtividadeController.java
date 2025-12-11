package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.cefetmg.sicsec.Service.AtividadeService;
import br.cefetmg.sicsec.dto.AtividadeDTO;
import br.cefetmg.sicsec.dto.HomeAtividadesDTO;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/atividade")
@CrossOrigin(origins = "*")

public class AtividadeController {
    @Autowired
    private AtividadeService atividadeService;

    @GetMapping("/{atividadeId}")
    public ResponseEntity<Atividade> getAtividade(@PathVariable Long atividadeId) {
        Atividade atividade = atividadeService.getAtividade(atividadeId);
        return ResponseEntity.ok(atividade);
    }

    @GetMapping("/atividades")
    public ResponseEntity<List<Atividade>> ListarAtividades() {
        List<Atividade> listaAtividades = atividadeService.ListarAtividades();
        return ResponseEntity.ok(listaAtividades);
    }

    @GetMapping("/home-atividades")
    public ResponseEntity<List<HomeAtividadesDTO>> ListarHomeAtividades() {
        List<HomeAtividadesDTO> listaAtividadesDTO = atividadeService.ListarAtividadesHomeAtividadeDTO();
        return ResponseEntity.ok(listaAtividadesDTO);
    }

    @GetMapping("/atividades-dto")
    public ResponseEntity<List<AtividadeDTO>> ListarAtividadesDTO(HttpSession session) {
        List<AtividadeDTO> listaAtividadesDTO = atividadeService.ListarAtividadesDTO(session);
        return ResponseEntity.ok(listaAtividadesDTO);
    }

    @PostMapping("/salvar")
    public ResponseEntity<?> salvarAtividade(
            @RequestPart("atividade") Atividade atividade,
            @RequestPart(value = "arquivos", required = false) MultipartFile[] arquivos,
            HttpSession session) throws IOException {

        Atividade nova = atividadeService.salvarAtividade(atividade, arquivos, session);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }
}
