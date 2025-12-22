package br.cefetmg.sicsec.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.TentativaRepository;
import br.cefetmg.sicsec.Repository.Usuarios.AlunoRepo;
import br.cefetmg.sicsec.dto.tentativa.TentativaCreateDTO;
import br.cefetmg.sicsec.dto.tentativa.TentativaRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TentativaService {
    @Autowired
    TentativaRepository tentativaRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    AlunoRepo alunoRepository;

    public Tentativa getTentativa(Long tentativaId) {
        return tentativaRepository.findById(tentativaId)
                .orElseThrow(() -> new EntityNotFoundException("Tentativa não encontrada"));
    }

    public TentativaRequestDTO salvarTentativa(TentativaCreateDTO dto, Usuario usuario) {
        Aluno aluno = alunoRepository.findById(usuario.getMatricula().getId())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        Atividade atividade = atividadeRepository.findById(dto.atividadeId())
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada"));

        Tentativa tentativa = toEntity(dto, aluno, atividade);
        return toDTO(tentativaRepository.save(tentativa));
    }

    public int getNumTentativasFeitas(Long atividadeId) {
        return tentativaRepository.countByAtividade_IdAndAbertaFalse(atividadeId);
    }

    public TentativaRequestDTO getTentativaAberta(Long atividadeId) {
        return tentativaRepository.findByAtividade_IdAndAbertaTrue(atividadeId)
                .map(this::toDTO)
                .orElse(null);
    }

    public TentativaRequestDTO getUltimaTentativa(Long atividadeId) {
        Tentativa tentativa = tentativaRepository.findTopByAtividade_IdOrderByNumTentativaDesc(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Tentativa não encontrada"));
        return toDTO(tentativa);
    }

    public void atualizarTimer(Long tentativaId, Map<String, Object> dados) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId).orElseThrow();
        if (dados.containsKey("tempoRestante")) {
            tentativa.setTempoRestante((Integer) dados.get("tempoRestante"));
        }
    }

    public void salvarTimerInterrompivel(Long atividadeId) {
        Tentativa tentativa = new Tentativa();
        Atividade atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada"));
        tentativa.setAtividade(atividade);
        tentativa.setAberta(true);
        tentativa.setHorarioInicio(LocalDateTime.now());
        tentativaRepository.save(tentativa);
    }

    public void salvarTimerContinuo(Long atividadeId) {
        Tentativa tentativa = new Tentativa();
        Atividade atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada"));
        tentativa.setAtividade(atividade);
        tentativa.setAberta(true);
        tentativa.setHorarioInicio(LocalDateTime.now());
        tentativa.setTempoRestante(0);
        tentativaRepository.save(tentativa);
    }

    public void fecharTentativa(Long id) {
        Tentativa tentativa = tentativaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tentativa não encontrada"));
        tentativa.setTempoRestante(0);
        tentativa.setAberta(false);
    }

    public Long getTempoRestante(Long tentativaId) {
        Tentativa tentativa = tentativaRepository.findById(tentativaId)
                .orElseThrow(() -> new EntityNotFoundException("Tentativa não encontrada"));
        LocalDateTime agora = LocalDateTime.now();
        Duration decorrido = Duration.between(tentativa.getHorarioInicio(), agora);
        long restante = tentativa.getTempoRestante() - decorrido.getSeconds();

        if (restante <= 0) {
            tentativa.setAberta(false);
            restante = 0;
        }

        return restante;
    }

    private Tentativa toEntity(TentativaCreateDTO dto, Aluno aluno, Atividade atividade) {
        Tentativa tentativa = new Tentativa();
        tentativa.setAberta(dto.aberta());
        tentativa.setAluno(aluno);
        tentativa.setAtividade(atividade);
        tentativa.setHorarioInicio(LocalDateTime.parse(dto.horarioInicio()));
        tentativa.setNumTentativa(dto.numTentativa());
        tentativa.setTempoRestante(dto.tempoRestante());
        return tentativa;
    }

    private TentativaRequestDTO toDTO(Tentativa tentativa) {
        return new TentativaRequestDTO(
                tentativa.getId(),
                tentativa.getAtividade().getId(),
                tentativa.getAluno().getId(),
                tentativa.getHorarioInicio().toLocalDate().toString(),
                tentativa.getTempoRestante(),
                tentativa.getNumTentativa(),
                tentativa.getAberta());
    }
}
