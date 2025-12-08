package br.cefetmg.sicsec.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.TentativaRepository;

@Service
public class TentativaService {
    @Autowired
    TentativaRepository tentativaRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    public Tentativa salvar(Long id) {
        Tentativa tentativa = new Tentativa();
        tentativa.setAtividade(atividadeRepository.findById(id).orElseThrow());
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

    public Tentativa fechar(Long id) {
        Tentativa tentativa = tentativaRepository.findById(id).orElseThrow();
        tentativa.setTempoRestante(0);
        tentativa.setAberta(false);
        return tentativa;
    }

    public Long getTempoRestante(Long id) {
        Tentativa status = tentativaRepository.findById(id).orElseThrow();
        LocalDateTime agora = LocalDateTime.now();
        Duration decorrido = Duration.between(status.getHorarioInicio(), agora);
        long restante = status.getTempoRestante() - decorrido.getSeconds();

        if (restante <= 0) {
            status.setAberta(false);
            tentativaRepository.save(status);
            restante = 0;
        }

        return restante;
    }
}
