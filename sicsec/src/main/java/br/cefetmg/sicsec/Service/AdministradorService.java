package br.cefetmg.sicsec.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Administrador;
import br.cefetmg.sicsec.Model.Usuario.Administrador.ChefeDepartamento;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Coordenador;
import br.cefetmg.sicsec.Model.Util.Enum.CargoAdministrador;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import br.cefetmg.sicsec.Repository.Usuarios.*;
import br.cefetmg.sicsec.dto.Perfil;
import jakarta.servlet.http.HttpSession;

@Service
public class AdministradorService {
    
    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private AdministradorRepo adminRepo;

    public Object GetAdministrador(HttpSession session) {

        Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
        Usuario usuario = perfil.getUsuario();
        
        if (usuario == null || usuario.getCargo() != Cargo.ADMINISTRADOR) {
            return new ResponseEntity<>(
                java.util.Map.of("erro", "Não é um administrador autenticado"),
                HttpStatus.FORBIDDEN
            );
        }

        Administrador adm = (Administrador) usuario;

        Map<String, Object> mapa = new HashMap<>();
        mapa.putAll( Map.of(
            "id", adm.getId(),
            "nome", adm.getMatricula().getNome(),
            "cargo", adm.getCargoAdministrador().toString()
        ));

        switch (adm.getCargoAdministrador()) {

            case CHEFE_DE_DEPARTAMENTO:
                ChefeDepartamento cdp = (ChefeDepartamento) adm;
                mapa.put("departamento", cdp.getDepartamento().getId());
                break;

            case COORDENADOR:
                Coordenador crd = (Coordenador) adm;
                mapa.put("curso", crd.getCurso().getId());
                break;

            case ROOT:
                break;
                
            default:
                throw new IllegalArgumentException("Cargo de administrador desconhecido: " + adm.getCargoAdministrador());

        }

        return mapa;

    }

}
