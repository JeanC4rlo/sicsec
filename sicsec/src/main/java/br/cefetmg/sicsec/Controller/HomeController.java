package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Service.HomeService;
import br.cefetmg.sicsec.dto.Perfil;
import br.cefetmg.sicsec.dto.Secao;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/home")
    public String homeView(HttpSession session,
            @RequestParam(required = false) Long idUsuario,
            RedirectAttributes redirectAttributes,
            Model model) {

        List<Perfil> perfis = (List<Perfil>) session.getAttribute("perfis");
        Perfil perfilSelecionado = (Perfil) session.getAttribute("perfilSelecionado");

        if (perfilSelecionado == null) {
            redirectAttributes.addFlashAttribute("error", "Acesse a visão de usuário com um login válido!");
            return "redirect:/login";
        }

        if (idUsuario != null) {
            perfilSelecionado = perfis.stream()
                    .filter(p -> p.getUsuario().getId().equals(idUsuario))
                    .findFirst()
                    .orElse(null);
        }

        List<Secao> secoes = homeService.getSecoesPorCargo(perfilSelecionado.getUsuario());

        model.addAttribute("secoes", secoes);
        model.addAttribute("perfis", perfis);
        model.addAttribute("perfilSelecionado", perfilSelecionado);

        return "home";
    }

    @GetMapping("/home/section")
    public ResponseEntity<?> carregarSecao(
            @RequestParam String id,
            HttpSession session) {

        Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");

        if (perfil == null || !perfil.getLogado()) {
            return ResponseEntity.status(403).cacheControl(CacheControl.noStore())
                .body(Map.of("error", "Acesso negado."));
        }

        Usuario usuario = perfil.getUsuario();

        try {
            Map<String, Object> data = homeService.getSecaoData(usuario, id);
            if (data.isEmpty()) {
                return ResponseEntity.status(404).cacheControl(CacheControl.noStore())
                        .body(Map.of("error", "Seção não encontrada ou não permitida."));
            }
            return ResponseEntity
                .ok().cacheControl(CacheControl.noStore())
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).cacheControl(CacheControl.noStore())
                    .body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(500).cacheControl(CacheControl.noStore())
                    .body(Map.of("error", "Erro ao carregar seção."));
        }
    }

    @GetMapping("/fazerAtividades")
    public String fazerAtividadesView(@RequestParam String param) {
        return "redirect:/html/aluno/fazer-atividades.html";
    }

    @GetMapping("/producao-atividades")
    public String producaoAtividadesView() {
        return "redirect:/html/professor/producao-atividades.html";
    }

}
