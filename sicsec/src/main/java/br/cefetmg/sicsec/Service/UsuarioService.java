package br.cefetmg.sicsec.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.UsuarioRepo;

/**
 *
 * @author davig
 */

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepo usuarioRepo;

    public Usuario getUsuarioFromSession(HttpServletRequest request) {
        return null;
    }

    public Iterable<Aluno> getAlunosByCurso(Long cursoId) {

        return usuarioRepo.findAlunosByCursoId(cursoId);
    }

}
