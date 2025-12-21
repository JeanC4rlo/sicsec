package br.cefetmg.sicsec.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.DesempenhoRepository;
import br.cefetmg.sicsec.Repository.TentativaRepository;
import br.cefetmg.sicsec.Repository.Usuarios.AlunoRepo;
import br.cefetmg.sicsec.dto.DesempenhoComDadosAlunoDTO;
import br.cefetmg.sicsec.dto.TurmaDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class DesempenhoService {
    @Autowired
    DesempenhoRepository desempenhoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    TentativaRepository tentativaRepository;

    @Autowired
    TurmaService turmaService;

    @Autowired
    AlunoRepo alunoRepository;

    public Double getNotaTentativa(Long tentativaId) {
        Desempenho desempenho = desempenhoRepository.findByTentativaId(tentativaId);
        return desempenho.getNota();
    }

    public Double getMaiorNotaAtividade(Long atividadeId, Long alunoId) {
        Double nota = null;
        Desempenho desempenho = desempenhoRepository.findTopByAtividadeIdAndAlunoIdOrderByNotaDesc(atividadeId,
                alunoId);
        if (desempenho != null)
            nota = desempenho.getNota();
        return nota;
    }

    public Desempenho getDesempenho(Long desempenhoId) {
        return desempenhoRepository.findById(desempenhoId).orElseThrow();
    }

    public List<DesempenhoComDadosAlunoDTO> getListaDesempenhos(HttpSession session) {
        List<TurmaDTO> turmasProfessor = turmaService.listarTurmasPorProfessor(session);
        return desempenhoRepository.findAll()
                .stream()
                .filter(desempenho -> {
                    Set<String> nomesTurmasAluno = desempenho.getAluno().getTurmas().stream()
                            .map(Turma::getNome)
                            .collect(Collectors.toSet());

                    return turmasProfessor.stream()
                            .map(TurmaDTO::nome)
                            .anyMatch(nomesTurmasAluno::contains);
                })
                .map(desempenho -> new DesempenhoComDadosAlunoDTO(
                        desempenho.getId(),
                        desempenho.getAluno().getMatricula().getNome(),
                        getNomeTurmaAluno(desempenho),
                        desempenho.getAtividade().getNome(),
                        desempenho.getTentativa().getNumTentativa(),
                        desempenho.getAtividade().getTipo(),
                        desempenho.getNota(),
                        desempenho.getAtividade().getValor()))
                .toList();
    }

    @Transactional
    public Desempenho salvarDesempenho(Resposta resposta, Double nota, Aluno aluno) {

        Desempenho desempenho = desempenhoRepository
                .findByResposta(resposta)
                .orElseGet(Desempenho::new);

        desempenho.setAluno(aluno);
        desempenho.setAtividade(resposta.getAtividade());
        desempenho.setResposta(resposta);
        desempenho.setTentativa(resposta.getTentativa());

        if (nota != null) {
            desempenho.setNota(nota);
            desempenho.setCorrigido(true);
        } else {
            desempenho.setNota(null);
            desempenho.setCorrigido(false);
        }

        return desempenhoRepository.save(desempenho);
    }

    public Desempenho atribuirNota(Long desempenhoId, Double nota) {
        Desempenho desempenho = desempenhoRepository.findById(desempenhoId).orElseThrow();
        desempenho.setNota(nota);
        desempenho.setCorrigido(true);
        return desempenhoRepository.save(desempenho);
    }

    // Corrigir função
    private String getNomeTurmaAluno(Desempenho desempenho) {
        List<Turma> turmasDoAluno = desempenho.getAluno().getTurmas();
        List<Turma> turmasDaAtividade = desempenho.getAtividade().getTurmas();
        System.out.println(turmasDaAtividade);
        return turmasDaAtividade.stream()
                .filter(turmasDoAluno::contains)
                .findFirst()
                .map(Turma::getNome)
                .orElse(null);
    }
}
