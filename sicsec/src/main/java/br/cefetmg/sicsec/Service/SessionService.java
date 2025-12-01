package br.cefetmg.sicsec.Service;

import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
    public void salvarDadosSessao(HttpSession session, Usuario usuario) {
        session.setAttribute("usuario", usuario.getMatricula().getNome());
        session.setAttribute("cargo", usuario.getCargo());
        session.setAttribute("usuarioId", usuario.getId());
    }
}
