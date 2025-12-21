package br.cefetmg.sicsec.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Administrador;
import br.cefetmg.sicsec.Model.Usuario.Administrador.ChefeDepartamento;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Coordenador;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;

@Service
public class AdministradorService {
    public Object GetAdministrador(Usuario usuario) {

        if (usuario == null || usuario.getCargo() != Cargo.ADMINISTRADOR) {
            return new org.springframework.http.ResponseEntity<>(
                java.util.Map.of("erro", "Não é um administrador autenticado"),
                org.springframework.http.HttpStatus.FORBIDDEN
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
