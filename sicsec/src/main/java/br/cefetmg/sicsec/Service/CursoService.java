package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Repository.CursoRepo;
import br.cefetmg.sicsec.Model.Curso.Curso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CursoService {

    @Autowired
    private CursoRepo cursoRepo;

    public Object getAll() {

        List<Curso> cursosDAO = cursoRepo.findAll();

        List<Object> cursos = new ArrayList<>();

        for (Curso curso : cursosDAO) {
            cursos.add(Map.of(
                    "id", curso.getId(),
                    "nome", curso.getNome()));
        }

        return cursos;

    }

    public Object getCursosByDepartamento(Long departamentoId) {

        List<Curso> cursosDAO = cursoRepo.findByDepartamentoId(departamentoId);

        List<Object> cursos = new ArrayList<>();

        for (Curso curso : cursosDAO) {
            cursos.add(Map.of(
                    "id", curso.getId(),
                    "nome", curso.getNome()));
        }

        return cursos;
    }

    public Curso getCursoById(Long cursoId) {
        return cursoRepo.findById(cursoId)
                .orElseThrow(() -> new IllegalStateException("Curso inválido"));
    }
}