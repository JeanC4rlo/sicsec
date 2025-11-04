package br.cefetmg.sicsec.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import br.cefetmg.sicsec.dto.StatusAtividadeDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin(origins = "*")
public class StatusAtividadeController {
    @Autowired
    StatusAtividadeRepository statusAtividadeRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @PostMapping("/salvar-status-atividade")
    public ResponseEntity<?> salvarStatusAtividade(@RequestBody StatusAtividadeDTO dto) {

        Optional<Atividade> atividadeOpt = atividadeRepository.findById(dto.atividade);
        if (atividadeOpt.isEmpty())
            return ResponseEntity.notFound().build();

        StatusAtividade status = new StatusAtividade();
        status.setAtividade(atividadeOpt.get());

        status.setHorarioInicio(LocalDateTime.parse(dto.horarioInicio,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        status.setTempoRestante(dto.tempoRestante);
        status.setNumTentativa(dto.numTentativa);
        status.setEnviada(dto.enviada);

        statusAtividadeRepository.save(status);
        return ResponseEntity.ok().build();
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

    @GetMapping("/atividade/{atividadeId}/ultima-tentativa")
    public ResponseEntity<StatusAtividade> buscarUltimaTentativa(@PathVariable Long atividadeId) {
        Optional<StatusAtividade> ultimaTentativa = statusAtividadeRepository
                .findTopByAtividade_IdOrderByNumTentativaDesc(atividadeId);

        return ultimaTentativa
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
