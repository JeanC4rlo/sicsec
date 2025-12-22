package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Repository.DisciplinaRepo;
import jakarta.persistence.EntityNotFoundException;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepo disciplinaRepo;

    public Disciplina getDisciplina(Long id) {
        return disciplinaRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Disciplina não encontrada"));
    }

    public List<Disciplina> getByCursoId(Long cursoId) {
        return disciplinaRepo.findByCursoId(cursoId);
    }

}
