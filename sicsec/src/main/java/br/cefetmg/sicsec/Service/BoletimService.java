package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Boletim;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import br.cefetmg.sicsec.Repository.BoletimRepo;
import br.cefetmg.sicsec.dto.Perfil;
import jakarta.servlet.http.HttpSession;

@Service
public class BoletimService {

    @Autowired
    BoletimRepo bRepo;

    public List<Boletim> acessarBoletim(HttpSession session) throws IllegalAccessException {

        Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");

        if (perfil == null) {
            throw new IllegalAccessException("Acesso restrito a alunos logados.");
        }

        Usuario usuario = perfil.getUsuario();

        if (usuario == null || usuario.getCargo() != Cargo.ALUNO) {
            throw new IllegalAccessException("Acesso restrito a alunos.");
        }

        List<Boletim> boletins = bRepo.findByAluno((Aluno) usuario);

        return boletins;
        
    }

}
