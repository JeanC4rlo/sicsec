package br.cefetmg.sicsec.Controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Service.TentativaService;
import br.cefetmg.sicsec.dto.Perfil;
import br.cefetmg.sicsec.dto.tentativa.TentativaCreateDTO;
import br.cefetmg.sicsec.dto.tentativa.TentativaRequestDTO;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tentativa")
public class TentativaController {
    @Autowired
    TentativaService tentativaService;

    @PostMapping("/salvar")
    public ResponseEntity<TentativaRequestDTO> salvarTentativa(@RequestBody TentativaCreateDTO dto,
            HttpSession session) {
        Usuario usuario = ((Perfil) session.getAttribute("perfilSelecionado")).getUsuario();
        return ResponseEntity.ok(tentativaService.salvarTentativa(dto, usuario));
    }

    @GetMapping("/atividade/{atividadeId}/num-tentativas")
    public Integer getNumTentativasAtividade(@PathVariable Long atividadeId) {
        return tentativaService.getNumTentativasFeitas(atividadeId);
    }

    @GetMapping("/atividade/{atividadeId}/tentativa-aberta")
    public ResponseEntity<TentativaRequestDTO> getTentativaAberta(@PathVariable Long atividadeId) {
        return ResponseEntity.ok(tentativaService.getTentativaAberta(atividadeId));
    }

    @GetMapping("/atividade/{atividadeId}/ultima-tentativa")
    public TentativaRequestDTO getUltimaTentativa(@PathVariable Long atividadeId) {
        return tentativaService.getUltimaTentativa(atividadeId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/atualizar-timer/{tentativaId}")
    public void atualizarTimer(
            @PathVariable Long tentativaId,
            @RequestBody Map<String, Object> dados) {
        tentativaService.atualizarTimer(tentativaId, dados);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/fechar/{tentativaId}")
    public void fecharTentativa(@PathVariable Long tentativaId) {
        tentativaService.fecharTentativa(tentativaId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/iniciar/atividade/{atividadeId}/timer-interrompivel")
    public void iniciarTentativaInterrompivel(@PathVariable Long atividadeId) {
        tentativaService.salvarTimerInterrompivel(atividadeId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/iniciar/atividade/{atividadeId}/timer-continuo")
    public void iniciarTentativaContinua(@PathVariable Long atividadeId) {
        tentativaService.salvarTimerContinuo(atividadeId);
    }

    @GetMapping("/tempo-restante/{tentativaId}")
    public Long tempoRestante(@PathVariable Long tentativaId) {
        return tentativaService.getTempoRestante(tentativaId);
    }
}