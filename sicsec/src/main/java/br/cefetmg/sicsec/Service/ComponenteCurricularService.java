package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Boletim;
import br.cefetmg.sicsec.Model.Usuario.Aluno.ComponenteCurricular;
import br.cefetmg.sicsec.Model.Util.Enum.Aprovacao;
import br.cefetmg.sicsec.Repository.ComponenteCurricularRepository;
import jakarta.transaction.Transactional;

@Service
public class ComponenteCurricularService {

    @Autowired
    private ComponenteCurricularRepository componenteCurricularRepository;

    @Transactional
    public void criarComponentesParaBoletim(Boletim boletim) {
        List<Disciplina> disciplinas = boletim.getAluno().getTurmas().stream()
                .map(Turma::getDisciplina)
                .distinct()
                .toList();

        for (Disciplina disciplina : disciplinas) {
            boolean existe = componenteCurricularRepository
                    .existsByBoletimIdAndDisciplinaId(boletim.getId(), disciplina.getId());

            if (!existe) {
                ComponenteCurricular cc = new ComponenteCurricular();
                cc.setBoletim(boletim);
                cc.setDisciplina(disciplina);
                cc.setNotaFinal(0);
                cc.setFaltas(0);
                cc.setSituacao(Aprovacao.EM_CURSO);

                componenteCurricularRepository.save(cc);
            }
        }
    }
}
