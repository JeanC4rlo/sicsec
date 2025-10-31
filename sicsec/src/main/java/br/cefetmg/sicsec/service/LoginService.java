package br.cefetmg.sicsec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import br.cefetmg.sicsec.Repository.UsuarioDAO;

@Service
public class LoginService {
    @Autowired
    private UsuarioDAO usuarioDAO;

    public UsuarioModel registerUsuario(UsuarioModel usuario) {
        return usuarioDAO.save(usuario);
    }

    public UsuarioModel authenticate(Long cpf, String senha) {
        List<UsuarioModel> usuarios = usuarioDAO.findByMatricula_Cpf(cpf);

        if (usuarios.isEmpty()) {
            return null;
        }

        UsuarioModel usuario = usuarios.get(0);
        if (senha.equals(usuario.getSenha())) {
            return usuario;
        }

        return null;
    }
}
