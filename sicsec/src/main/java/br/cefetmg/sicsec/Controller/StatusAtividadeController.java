package br.cefetmg.sicsec.Controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.StatusAtividade;
import br.cefetmg.sicsec.Repository.StatusAtividadeRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "*")
public class StatusAtividadeController {
    @Autowired
    StatusAtividadeRepository statusAtividadeRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @PostMapping("/salvar-status-atividade")
    public ResponseEntity<StatusAtividade> salvarStatusAtividade(@RequestBody StatusAtividade statusAtividade) {
        if (statusAtividade.getAtividade() == null || statusAtividade.getAtividade().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Atividade> atividadeOpt = atividadeRepository.findById(statusAtividade.getAtividade().getId());
        if (atividadeOpt.isEmpty())
            return ResponseEntity.notFound().build();

        statusAtividade.setAtividade(atividadeOpt.get());

        StatusAtividade salvo = statusAtividadeRepository.save(statusAtividade);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/atividade/{atividadeId}/tentativas")
    public ResponseEntity<Integer> consultarNumTentativas(@PathVariable Long atividadeId) {

        Optional<Atividade> atividadeOpt = atividadeRepository.findById(atividadeId);
        if (atividadeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Atividade atividade = atividadeOpt.get();
        if (atividade == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        long tentativas = statusAtividadeRepository.countByAtividade_Id(atividadeId);

        return ResponseEntity.ok((int) tentativas);
    }

    @GetMapping("/atividade/{atividadeId}/tentativa-aberta")
    public StatusAtividade getTentativasAbertas(@PathVariable Long atividadeId) {
        return statusAtividadeRepository.findByAtividadeIdAndAbertaTrue(atividadeId)
                .orElse(null);
    }

    @GetMapping("/atividade/{atividadeId}/ultima-tentativa")
    public ResponseEntity<StatusAtividade> buscarUltimaTentativa(@PathVariable Long atividadeId) {
        Optional<StatusAtividade> ultimaTentativa = statusAtividadeRepository
                .findTopByAtividade_IdOrderByNumTentativaDesc(atividadeId);

        return ultimaTentativa
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("atualizar-status-tentativa/{tentativaId}/atualizar-tempo")
    public StatusAtividade atualizarTempo(
            @PathVariable Long tentativaId,
            @RequestBody Map<String, Object> dados) {
        StatusAtividade tentativa = statusAtividadeRepository.findById(tentativaId)
                .orElseThrow(() -> new RuntimeException("Tentativa não encontrada"));

        if (dados.containsKey("tempoRestante")) {
            tentativa.setTempoRestante((Integer) dados.get("tempoRestante"));
        }

        return statusAtividadeRepository.save(tentativa);
    }

    @PatchMapping("atualizar-status-tentativa/{tentativaId}/fechar-tentativa")
    public StatusAtividade fecharTentativa(@PathVariable Long tentativaId) {
        StatusAtividade tentativa = statusAtividadeRepository.findById(tentativaId)
                .orElseThrow(() -> new RuntimeException("Tentativa não encontrada"));
        tentativa.setTempoRestante(0);
        tentativa.setAberta(false);

        return statusAtividadeRepository.save(tentativa);
    }

    @PostMapping("/iniciar-tentativa/{atividadeId}")
    public ResponseEntity<StatusAtividade> iniciarTentativa(@PathVariable Long atividadeId) {
        StatusAtividade status = new StatusAtividade();
        status.setAtividade(atividadeRepository.findById(atividadeId).orElseThrow());
        status.setAberta(true);
        status.setHorarioInicio(LocalDateTime.now());
        statusAtividadeRepository.save(status);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/iniciar-tentativa/{atividadeId}/continuo")
    public ResponseEntity<StatusAtividade> iniciarTentativaContinua(@PathVariable Long atividadeId) {
        StatusAtividade status = new StatusAtividade();
        status.setAtividade(atividadeRepository.findById(atividadeId).orElseThrow());
        status.setAberta(true);
        status.setHorarioInicio(LocalDateTime.now());
        status.setTempoRestante(0);
        statusAtividadeRepository.save(status);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/tempo-restante/{statusAtividadeId}")
    public ResponseEntity<Long> tempoRestante(@PathVariable Long statusAtividadeId) {
        StatusAtividade status = statusAtividadeRepository.findById(statusAtividadeId)
                .orElseThrow();

        LocalDateTime agora = LocalDateTime.now();
        Duration decorrido = Duration.between(status.getHorarioInicio(), agora);
        long restante = status.getTempoRestante() - decorrido.getSeconds();

        System.out.println("Início: " + status.getHorarioInicio());
        System.out.println("Agora: " + agora);
        System.out.println("Decorrido (s): " + decorrido.getSeconds());
        System.out.println("Tempo restante salvo: " + status.getTempoRestante());

        if (restante <= 0) {
            status.setAberta(false);
            statusAtividadeRepository.save(status);
            restante = 0;
        }

        return ResponseEntity.ok(restante);
    }
}
