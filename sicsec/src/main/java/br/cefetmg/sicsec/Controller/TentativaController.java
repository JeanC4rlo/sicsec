package br.cefetmg.sicsec.Controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Service.TentativaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Tentativa> salvarTentativa(@RequestBody Tentativa tentativa) {
        Tentativa salva = tentativaService.salvarTentativa(tentativa);
        return ResponseEntity.ok(salva);
    }

    @GetMapping("/atividade/{atividadeId}/num-tentativas")
    public ResponseEntity<Integer> getNumTentativasAtividade(@PathVariable Long atividadeId) {
        int tentativas = tentativaService.getNumTentativasAtividade(atividadeId);
        return ResponseEntity.ok(tentativas);
    }

    @GetMapping("/atividade/{atividadeId}/tentativa-aberta")
    public ResponseEntity<Tentativa> getTentativaAberta(@PathVariable Long atividadeId) {
        Tentativa aberta = tentativaService.getTentativaAberta(atividadeId);
        return ResponseEntity.ok(aberta);
    }

    @GetMapping("/atividade/{atividadeId}/ultima-tentativa")
    public ResponseEntity<Tentativa> getUltimaTentativa(@PathVariable Long atividadeId) {
         Tentativa ultima = tentativaService.getUltimaTentativa(atividadeId);
         return ResponseEntity.ok(ultima);
    }

    @PatchMapping("/atualizar-timer/{tentativaId}")
    public ResponseEntity<Tentativa> atualizarTimer(
            @PathVariable Long tentativaId,
            @RequestBody Map<String, Object> dados) {
        Tentativa nova = tentativaService.atualizarTimer(tentativaId, dados);
        return ResponseEntity.ok(nova);
    }

    @PatchMapping("/fechar/{tentativaId}")
    public ResponseEntity<Tentativa> fecharTentativa(@PathVariable Long tentativaId) {
        Tentativa nova = tentativaService.fecharTentativa(tentativaId);
        return ResponseEntity.ok(nova);
    }

    @PostMapping("/iniciar/atividade/{atividadeId}/timer-interrompivel")
    public ResponseEntity<Tentativa> iniciarTentativaInterrompivel(@PathVariable Long atividadeId) {
        Tentativa tentativa = tentativaService.salvarTimerInterrompivel(atividadeId);
        return ResponseEntity.ok(tentativa);
    }

    @PostMapping("/iniciar/atividade/{atividadeId}/timer-continuo")
    public ResponseEntity<Tentativa> iniciarTentativaContinua(@PathVariable Long atividadeId) {
        Tentativa tentativa = tentativaService.salvarTimerContinuo(atividadeId);
        return ResponseEntity.ok(tentativa);
    }

    @GetMapping("/tempo-restante/{tentativaId}")
    public ResponseEntity<Long> tempoRestante(@PathVariable Long tentativaId) {
        long restante = tentativaService.getTempoRestante(tentativaId);
        return ResponseEntity.ok(restante);
    }
}
