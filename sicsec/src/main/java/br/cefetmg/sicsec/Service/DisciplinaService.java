package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Repository.DisciplinaRepo;
import br.cefetmg.sicsec.Repository.Usuarios.ProfessorRepo;
import jakarta.persistence.EntityNotFoundException;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepo disciplinaRepo;

    @Autowired
    private ProfessorRepo professorRepo;
    
    public Disciplina getDisciplina(Long id) {
        return disciplinaRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada"));
    }

    public List<Disciplina> getByCursoId(Long cursoId) {
        return disciplinaRepo.findByCursoId(cursoId);
    }

    public Disciplina getDisciplinaByProfessor(Long professorId) {

        Professor professor = professorRepo.findById(professorId).orElse(null);
        if (professor == null) {
            return null;
        }

        List<Turma> turmas = professor.getTurmas();
        if (turmas.isEmpty()) {
            return null;
        }

        Turma turma = turmas.get(0);
        return turma.getDisciplina();

    }

}
