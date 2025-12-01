package br.cefetmg.sicsec.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.TentativaRepository;
import br.cefetmg.sicsec.Repository.Usuarios.UsuarioRepo;
import jakarta.servlet.http.HttpSession;

@Service
public class TentativaService {
    @Autowired
    TentativaRepository tentativaRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    UsuarioRepo usuarioRepository;

    public Tentativa salvarTentativa(Tentativa tentativa, HttpSession session) {
        Atividade atividade = atividadeRepository.findById(tentativa.getAtividade().getId()).orElseThrow();
        Usuario aluno = usuarioRepository.findById((Long) session.getAttribute("usuarioId")).get();
        tentativa.setAtividade(atividade);
        tentativa.setAluno(aluno);
        return tentativaRepository.save(tentativa);
    }

    public int getNumTentativasAtividade(Long atividadeId) {
        return tentativaRepository.countByAtividade_IdAndAbertaFalse(atividadeId);
    }

    public Tentativa getTentativaAberta(Long atividadeId) {
        return tentativaRepository.findByAtividadeIdAndAbertaTrue(atividadeId).orElse(null);
    }

    public Tentativa getUltimaTentativa(Long atividadeId) {
        return tentativaRepository.findTopByAtividade_IdOrderByNumTentativaDesc(atividadeId).get();
    }

    public Tentativa atualizarTimer(Long tentativaId, Map<String, Object> dados) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId).orElseThrow();
        if (dados.containsKey("tempoRestante")) {
            tentativa.setTempoRestante((Integer) dados.get("tempoRestante"));
        }
        return tentativaRepository.save(tentativa);
    }

    public Tentativa salvarTimerInterrompivel(Long atividadeId) {
        Tentativa tentativa = new Tentativa();
        tentativa.setAtividade(atividadeRepository.findById(atividadeId).orElseThrow());
        tentativa.setAberta(true);
        tentativa.setHorarioInicio(LocalDateTime.now());
        tentativaRepository.save(tentativa);
        return tentativa;
    }

    public Tentativa salvarTimerContinuo(Long id) {
        Tentativa tentativa = new Tentativa();
        tentativa.setAtividade(atividadeRepository.findById(id).orElseThrow());
        tentativa.setAberta(true);
        tentativa.setHorarioInicio(LocalDateTime.now());
        tentativa.setTempoRestante(0);
        tentativaRepository.save(tentativa);
        return tentativa;
    }

    public Tentativa fecharTentativa(Long id) {
        Tentativa tentativa = tentativaRepository.findById(id).orElseThrow();
        tentativa.setTempoRestante(0);
        tentativa.setAberta(false);
        return tentativaRepository.save(tentativa);
    }

    public Long getTempoRestante(Long tentativaId) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId).orElseThrow();
        LocalDateTime agora = LocalDateTime.now();
        Duration decorrido = Duration.between(tentativa.getHorarioInicio(), agora);
        long restante = tentativa.getTempoRestante() - decorrido.getSeconds();

        if (restante <= 0) {
            tentativa.setAberta(false);
            tentativaRepository.save(tentativa);
            restante = 0;
        }

        return restante;
    }
}
