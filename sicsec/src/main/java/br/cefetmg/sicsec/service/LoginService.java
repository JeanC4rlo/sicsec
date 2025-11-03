package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.UsuarioRepo;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepo usuarioRepo;

    public Usuario registerUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public Usuario authenticate(Long cpf, String senha) {
        List<Usuario> usuarios = usuarioRepo.findByCpf(cpf);

        if (usuarios.isEmpty()) {
            return null;
        }

        Usuario usuario = usuarios.get(0);
        if (senha.equals(usuario.getSenha())) {
            return usuario;
        }

        return null;
    }
}
