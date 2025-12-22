package br.cefetmg.sicsec.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.cefetmg.sicsec.Model.Usuario.Assinatura;
import br.cefetmg.sicsec.Model.Usuario.Documento;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.StatusAssinatura;
import br.cefetmg.sicsec.Model.Util.Enum.StatusDocumento;
import br.cefetmg.sicsec.Service.AssinaturaService;
import br.cefetmg.sicsec.dto.Perfil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/assinatura")
public class AssinaturaController {
    @Autowired
    private AssinaturaService assinaturaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Map<String, Object>>> listarAssinaturasPorUsuario(HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            if (perfil == null) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = perfil.getUsuario();
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }

            List<Assinatura> assinaturas = assinaturaService.buscarAssinaturasPorUsuario(usuario);

            List<Map<String, Object>> assinaturasMap = new ArrayList<>();
            for (Assinatura assinatura : assinaturas) {
                Documento documento = assinatura.getDocumento();
                Date dataExpiracao = documento.getDataExpiracao();

                StatusDocumento statusDoc = assinatura.getDocumento().getStatus();
                if(statusDoc == StatusDocumento.PENDENTE) {
                    statusDoc = dataExpiracao.before(new Date()) ? StatusDocumento.EXPIRADO : StatusDocumento.PENDENTE;
                }
                
                StatusAssinatura statusAssinatura = assinatura.getStatus();
                if(statusAssinatura == StatusAssinatura.PENDENTE) {
                    statusAssinatura = dataExpiracao.before(new Date()) ? StatusAssinatura.ATRASADA : StatusAssinatura.PENDENTE;
                }

                Map<String, Object> assinaturaData = Map.of(
                        "id", assinatura.getId(),
                        "documento", Map.of(
                                "id", assinatura.getDocumento().getId(),
                                "titulo", assinatura.getDocumento().getTitulo(),
                                "conteudo", assinatura.getDocumento().getConteudo(),
                                "dataCriacao", documento.getDataCriacao(),
                                "dataExpiracao", dataExpiracao,
                                "status", statusDoc.name()),
                        "usuarioId", assinatura.getUsuario().getId(),
                        "dataCriacao",
                        assinatura.getDataCriacao() != null ? assinatura.getDataCriacao() : "",
                        "dataAssinatura",
                        assinatura.getDataAssinatura() != null ? assinatura.getDataAssinatura() : "",
                        "status", statusAssinatura.name());
                assinaturasMap.add(assinaturaData);
            }

            return ResponseEntity.ok(assinaturasMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/assinar")
    public ResponseEntity<Map<String, Object>> assinarDocumento(@RequestParam Long assinaturaId, HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            if (perfil == null) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = perfil.getUsuario();
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }

            Assinatura assinatura = assinaturaService.atualizarAssinatura(assinaturaId, StatusAssinatura.CONFIRMADA, usuario);
            
            Map<String, Object> assinaturaMap = Map.of("id", assinatura.getId(),
                    "documento", Map.of(
                        "id", assinatura.getDocumento().getId(),
                        "titulo", assinatura.getDocumento().getTitulo(),
                        "conteudo", assinatura.getDocumento().getConteudo(),
                        "dataCriacao", assinatura.getDocumento().getDataCriacao(),
                        "dataExpiracao", assinatura.getDocumento().getDataExpiracao(),
                        "status", assinatura.getDocumento().getStatus().name()   
                    ),
                    "usuarioId", assinatura.getUsuario().getId(),
                    "dataCriacao", assinatura.getDataCriacao(),
                    "dataAssinatura", assinatura.getDataAssinatura(),
                    "status", assinatura.getStatus().name());

            return ResponseEntity.ok(assinaturaMap);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/rejeitar")
    public ResponseEntity<Map<String, Object>> rejeitarDocumento(@RequestParam Long assinaturaId, HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            if (perfil == null) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = perfil.getUsuario();
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }

            Assinatura assinatura = assinaturaService.atualizarAssinatura(assinaturaId, StatusAssinatura.REJEITADA, usuario);

            Map<String, Object> assinaturaMap = Map.of("id", assinatura.getId(),
                    "documento", Map.of(
                        "id", assinatura.getDocumento().getId(),
                        "titulo", assinatura.getDocumento().getTitulo(),
                        "conteudo", assinatura.getDocumento().getConteudo(),
                        "dataCriacao", assinatura.getDocumento().getDataCriacao(),
                        "dataExpiracao", assinatura.getDocumento().getDataExpiracao(),
                        "status", assinatura.getDocumento().getStatus().name()   
                    ),
                    "usuarioId", assinatura.getUsuario().getId(),
                    "dataCriacao", assinatura.getDataCriacao(),
                    "dataAssinatura", assinatura.getDataAssinatura(),
                    "status", assinatura.getStatus().name());

            return ResponseEntity.ok(assinaturaMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
