package br.cefetmg.sicsec.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Service.AdministradorService;
import br.cefetmg.sicsec.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    
    @Autowired
    private UsuarioService uService;

    @Autowired
    private AdministradorService admService;
    
    @PostMapping("/atual")
    public ResponseEntity<Object> getUsuarioAtual(HttpSession session) {


        Usuario usuario = uService.getUsuarioFromSession(session);

        if (usuario == null) {
            return new org.springframework.http.ResponseEntity<>(
                java.util.Map.of("erro", "Usuário não autenticado"),
                org.springframework.http.HttpStatus.UNAUTHORIZED
            );
        }

        Map<String, Object> mapaUsuario = Map.of(
            "id", usuario.getId(),
            "nome", usuario.getMatricula().getNome(),
            "cargo", usuario.getCargo().toString()
        );

        return ResponseEntity.ok(mapaUsuario);

    }

    @PostMapping("/atual/admin")
    public ResponseEntity<Object> getAdminAtual(HttpSession session) {

        Object adm = admService.GetAdministrador((Usuario) session.getAttribute("usuario"));

        return ResponseEntity.ok(adm);

    }

}
