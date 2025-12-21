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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Service.AtividadeService;
import br.cefetmg.sicsec.dto.HomeAtividadesDTO;
import br.cefetmg.sicsec.dto.Perfil;
import br.cefetmg.sicsec.dto.atividade.AtividadeAlunoDTO;
import br.cefetmg.sicsec.dto.atividade.AtividadeCreateDTO;
import br.cefetmg.sicsec.dto.atividade.AtividadeHomeDTO;
import br.cefetmg.sicsec.dto.atividade.AtividadeResumoDTO;
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

    @GetMapping("/completa/{atividadeId}")
    public ResponseEntity<AtividadeAlunoDTO> getAtividadeCompleta(@PathVariable Long atividadeId) {
        AtividadeAlunoDTO dto = atividadeService.getAtividadeDTO(atividadeId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/atividades")
    public ResponseEntity<List<AtividadeResumoDTO>> ListarAtividades() {
        List<AtividadeResumoDTO> listaAtividades = atividadeService.ListarAtividades();
        return ResponseEntity.ok(listaAtividades);
    }

    @GetMapping("/home-atividades")
    public ResponseEntity<List<HomeAtividadesDTO>> ListarHomeAtividades() {
        List<HomeAtividadesDTO> listaAtividadesDTO = atividadeService.ListarAtividadesHomeAtividadeDTO();
        return ResponseEntity.ok(listaAtividadesDTO);
    }

    @GetMapping("/atividades-dto")
    public ResponseEntity<List<AtividadeHomeDTO>> listarAtividadesDTO(HttpSession session) {
        Usuario usuario = ((Perfil) session.getAttribute("perfilSelecionado")).getUsuario();
        return ResponseEntity.ok(atividadeService.ListarAtividadesDTO(usuario));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/salvar")
    public void salvarAtividade(
            @RequestPart("atividade") AtividadeCreateDTO dto,
            @RequestPart(value = "arquivos", required = false) MultipartFile[] arquivos,
            HttpSession session) throws IOException {

        Usuario usuario = ((Perfil) session.getAttribute("perfilSelecionado")).getUsuario();
        atividadeService.salvarAtividade(dto, arquivos, usuario);
    }
}
