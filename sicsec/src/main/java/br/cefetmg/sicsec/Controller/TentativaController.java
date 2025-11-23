package br.cefetmg.sicsec.Controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Repository.TentativaRepository;
import br.cefetmg.sicsec.Service.TentativaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")
public class TentativaController {
    @Autowired
    TentativaRepository tentativaRepository;

    @Autowired
    TentativaService tentativaService;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @PostMapping("/salvar-status-atividade")
    public ResponseEntity<Tentativa> salvarStatusAtividade(@RequestBody Tentativa tentativa) {
        Atividade atividade = atividadeRepository.findById(tentativa.getAtividade().getId()).orElseThrow();
        tentativa.setAtividade(atividade);
        Tentativa salva = tentativaRepository.save(tentativa);
        return ResponseEntity.ok(salva);
    }

    @GetMapping("/atividade/{atividadeId}/tentativas")
    public ResponseEntity<Integer> consultarNumTentativas(@PathVariable Long atividadeId) {
        int tentativas = tentativaRepository.countByAtividade_IdAndAbertaFalse(atividadeId);
        return ResponseEntity.ok(tentativas);
    }

    @GetMapping("/atividade/{atividadeId}/tentativa-aberta")
    public ResponseEntity<?> getTentativasAbertas(@PathVariable Long atividadeId) {
        return tentativaRepository.findByAtividadeIdAndAbertaTrue(atividadeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }

    @GetMapping("/atividade/{atividadeId}/ultima-tentativa")
    public ResponseEntity<Tentativa> buscarUltimaTentativa(@PathVariable Long atividadeId) {
         return tentativaRepository.findTopByAtividade_IdOrderByNumTentativaDesc(atividadeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(null));
    }

    @PatchMapping("atualizar-status-tentativa/{tentativaId}/atualizar-tempo")
    public Tentativa atualizarTempo(
            @PathVariable Long tentativaId,
            @RequestBody Map<String, Object> dados) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId).orElseThrow();
        if (dados.containsKey("tempoRestante")) {
            tentativa.setTempoRestante((Integer) dados.get("tempoRestante"));
        }
        return tentativaRepository.save(tentativa);
    }

    @PatchMapping("atualizar-status-tentativa/{tentativaId}/fechar-tentativa")
    public Tentativa fecharTentativa(@PathVariable Long tentativaId) {
        Tentativa tentativa = tentativaService.fechar(tentativaId);
        return tentativaRepository.save(tentativa);
    }

    @PostMapping("/iniciar-tentativa/{atividadeId}")
    public ResponseEntity<Tentativa> iniciarTentativa(@PathVariable Long atividadeId) {
        Tentativa tentativa = tentativaService.salvar(atividadeId);
        return ResponseEntity.ok(tentativa);
    }

    @PostMapping("/iniciar-tentativa/{atividadeId}/continuo")
    public ResponseEntity<Tentativa> iniciarTentativaContinua(@PathVariable Long atividadeId) {
        Tentativa tentativa = tentativaService.salvarTimerContinuo(atividadeId);
        return ResponseEntity.ok(tentativa);
    }

    @GetMapping("/tempo-restante/{tentativaId}")
    public ResponseEntity<Long> tempoRestante(@PathVariable Long tentativaId) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId).orElseThrow();
        LocalDateTime agora = LocalDateTime.now();
        Duration decorrido = Duration.between(tentativa.getHorarioInicio(), agora);
        long restante = tentativa.getTempoRestante() - decorrido.getSeconds();

        if (restante <= 0) {
            tentativa.setAberta(false);
            tentativaRepository.save(tentativa);
            restante = 0;
        }

        return ResponseEntity.ok(restante);
    }
}
