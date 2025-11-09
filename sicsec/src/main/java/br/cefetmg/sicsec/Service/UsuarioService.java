package br.cefetmg.sicsec.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;

import br.cefetmg.sicsec.Repository.CursoRepo;
import br.cefetmg.sicsec.Repository.Usuarios.*;

/**
 *
 * @author davig
 */

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired 
    private AlunoRepo alunoRepo;

    @Autowired
    private CursoRepo cursoRepo;

    @Autowired
    private ProfessorRepo professorRepo;

    public Usuario getUsuarioFromSession(HttpServletRequest request) {
        return null;
    }

    public List<Aluno> getAlunosByCurso(Long cursoId) {

        return alunoRepo.findAllByCurso(cursoId);
    }

    public List<Professor> getProfessoresByDisciplina(Long disciplinaId) {


        Curso curso = cursoRepo.findByDisciplina(disciplinaId);
        List<Professor> professores = professorRepo.findAllByCurso(curso.getId());

        return professores;

    }

}
