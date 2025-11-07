package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Repository.CursoRepo;
import br.cefetmg.sicsec.Model.Curso.Curso;
import java.util.List;

@Service
public class CursoService {


    @Autowired
    private CursoRepo cursoRepo;

    public List<Curso> getAll() {
        return cursoRepo.findAll();
    }

    public List<Curso> getCursosByDepartamento(Long departamentoId) {
        return cursoRepo.findByDepartamentoId(departamentoId);
    }

    public Curso getCursoById(Long cursoId) {
        return cursoRepo.findById(cursoId).orElseThrow(() -> new IllegalStateException("Curso Invalido"));
    }

}
