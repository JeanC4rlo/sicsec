package br.cefetmg.sicsec.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Curso.Turma.presenca.Presenca;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;

import br.cefetmg.sicsec.Repository.CursoRepo;
import br.cefetmg.sicsec.Repository.PresencaRepo;
import br.cefetmg.sicsec.Repository.TurmaRepo;
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

    @Autowired
    private PresencaRepo presencaRepo;

    @Autowired
    private TurmaRepo turmaRepo;

    public Usuario getUsuarioFromSession(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        return usuario;

    }

    public List<Aluno> getAlunosByCurso(Long cursoId) {

        return alunoRepo.findAllByCurso(cursoId);
    }

    public List<Professor> getProfessoresByDisciplina(Long disciplinaId) {

        Curso curso = cursoRepo.findByDisciplina(disciplinaId);

        List<Professor> professores = professorRepo.findAllByDepartamentoId(curso.getDepartamento().getId());

        return professores;

    }

    public List<Presenca> getFrequenciaAluno(HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null || !(usuario instanceof Aluno)) {
            throw new IllegalStateException("Acesso negado.");
        }
        
        Long alunoId = usuario.getId();

        return presencaRepo.findByDiscenteId(alunoId);

    }

    public List<Presenca> getFrequenciaEmTurma(HttpSession session, Long turmaId) {

        List<Presenca> presencas = getFrequenciaAluno(session);

        Turma turma = turmaRepo.findById(turmaId).orElse(null);
        if (turma == null) {
            throw new IllegalArgumentException("Turma não encontrada.");
        }

        presencas = presencas.stream()
            .filter(p -> p.getLista().getTurma().getId().equals(turmaId))
            .toList();

        return presencas;

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
}
