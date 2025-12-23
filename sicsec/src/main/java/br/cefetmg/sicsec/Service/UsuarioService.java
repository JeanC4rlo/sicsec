package br.cefetmg.sicsec.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Bibliotecario.Bibliotecario;
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

    public Usuario getUsuarioFromSession(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        return usuario;


    }

    public List<Aluno> getAlunosByCurso(Long cursoId) {

        return alunoRepo.findAllByCurso(cursoId);
    }

    public List<Professor> getProfessoresByDisciplina(Long disciplinaId) {


        Curso curso = cursoRepo.findByDisciplina(disciplinaId);
        List<Professor> professores = professorRepo.findAllByCurso(curso.getId());

        return professores;

    }

    public Optional<Usuario> findById(Long idUsuario) {
        return usuarioRepo.findById(idUsuario);
    }

    public List<Usuario> buscarUsuariosPorNomeMatriculaCpf(String q) {
        return usuarioRepo.findByNomeMatriculaCpf(q);
    }

    public List<Usuario> findAllById(List<Long> ids) {
        return usuarioRepo.findAllById(ids);
    }

    public Optional<Usuario> findByNumeroMatricula(Long matriculaLong) {
        return usuarioRepo.findByMatricula_NumeroMatricula(matriculaLong);
    }
}
