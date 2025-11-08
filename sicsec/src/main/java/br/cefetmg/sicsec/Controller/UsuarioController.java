package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Administrador;
import br.cefetmg.sicsec.Model.Usuario.Administrador.ChefeDepartamento;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Coordenador;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import br.cefetmg.sicsec.Service.AdministradorService;
import br.cefetmg.sicsec.Service.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    
    @Autowired
    private UsuarioService uService;

    @Autowired
    private AdministradorService admService;
    
    @PostMapping("/atual")
    public Object getUsuarioAtual(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return new org.springframework.http.ResponseEntity<>(
                java.util.Map.of("erro", "Usuário não autenticado"),
                org.springframework.http.HttpStatus.UNAUTHORIZED
            );
        }

        // Evita enviar dados sensíveis (como senha)
        return java.util.Map.of(
            "id", usuario.getId(),
            "nome", usuario.getMatricula().getNome(),
            "cargo", usuario.getCargo().toString()
        );
    }

    @PostMapping("/atual/admin")
    public Object getAdminAtual(HttpSession session) {

        return admService.GetAdministrador((Usuario) session.getAttribute("usuario"));

    }

}
