package br.cefetmg.sicsec.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Usuario.Documento;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.StatusDocumento;
import br.cefetmg.sicsec.Service.DocumentoService;
import br.cefetmg.sicsec.Service.UsuarioService;
import br.cefetmg.sicsec.dto.Perfil;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private UsuarioService usuarioService;

    private final String UPLOAD_DIR = "uploads/documentos/";

    @GetMapping("/list")
    public ResponseEntity<List<Map<String, Object>>> getDocumentosByUsuario(@RequestParam Long idUsuario) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.findById(idUsuario);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = usuarioOpt.get();
            List<Documento> documentos = documentoService.getDocumentos(usuario);

            List<Map<String, Object>> documentosMap = new ArrayList<>();

            for (Documento doc : documentos) {
                List<Map<String, Object>> assinaturasList = new ArrayList<>();
                if (doc.getAssinaturas() != null) {
                    for (var assinatura : doc.getAssinaturas()) {
                        assinaturasList.add(Map.of(
                                "id", assinatura.getId(),
                                "usuarioId", assinatura.getUsuario() != null ? assinatura.getUsuario().getId() : 0,
                                "dataCriacao", assinatura.getDataCriacao() != null ? assinatura.getDataCriacao() : "",
                                "dataAssinatura",
                                assinatura.getDataAssinatura() != null ? assinatura.getDataAssinatura() : "",
                                "status",
                                assinatura.getStatus() != null ? assinatura.getStatus().name() : "DESCONHECIDO"));
                    }
                }

                documentosMap.add(Map.of(
                        "id", doc.getId(),
                        "titulo", doc.getTitulo() != null ? doc.getTitulo() : "",
                        "conteudo", doc.getConteudo() != null ? doc.getConteudo() : "",
                        "dataCriacao", doc.getDataCriacao() != null ? doc.getDataCriacao() : "",
                        "dataExpiracao", doc.getDataExpiracao() != null ? doc.getDataExpiracao() : "",
                        "status", doc.getStatus() != null ? doc.getStatus().name() : "DESCONHECIDO",
                        "assinaturas", assinaturasList));
            }

            return ResponseEntity.ok(documentosMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list/{status}")
    public ResponseEntity<List<Map<String, Object>>> getDocumentosByStatus(
            @PathVariable String status,
            @RequestParam Long idUsuario) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.findById(idUsuario);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuario = usuarioOpt.get();

            StatusDocumento statusEnum;
            try {
                statusEnum = StatusDocumento.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }

            List<Documento> documentos = documentoService.getDocumentosPorStatus(usuario, statusEnum);

            List<Map<String, Object>> documentosMap = new ArrayList<>();

            for (Documento doc : documentos) {
                List<Map<String, Object>> assinaturasList = new ArrayList<>();
                if (doc.getAssinaturas() != null) {
                    for (var assinatura : doc.getAssinaturas()) {
                        assinaturasList.add(Map.of(
                                "id", assinatura.getId(),
                                "usuarioId", assinatura.getUsuario() != null ? assinatura.getUsuario().getId() : 0,
                                "dataCriacao", assinatura.getDataCriacao() != null ? assinatura.getDataCriacao() : "",
                                "dataAssinatura",
                                assinatura.getDataAssinatura() != null ? assinatura.getDataAssinatura() : "",
                                "status",
                                assinatura.getStatus() != null ? assinatura.getStatus().name() : "DESCONHECIDO"));
                    }
                }

                documentosMap.add(Map.of(
                        "id", doc.getId(),
                        "titulo", doc.getTitulo() != null ? doc.getTitulo() : "",
                        "conteudo", doc.getConteudo() != null ? doc.getConteudo() : "",
                        "dataCriacao", doc.getDataCriacao() != null ? doc.getDataCriacao() : "",
                        "dataExpiracao", doc.getDataExpiracao() != null ? doc.getDataExpiracao() : "",
                        "status", doc.getStatus() != null ? doc.getStatus().name() : "DESCONHECIDO",
                        "assinaturas", assinaturasList));
            }

            return ResponseEntity.ok(documentosMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDocumentoPorId(@PathVariable Long id, HttpSession session) {
        try {
            Perfil perfilAtual = (Perfil) session.getAttribute("perfilSelecionado");
            if (perfilAtual == null) {
                return ResponseEntity.badRequest().build();
            }

            Usuario usuario = perfilAtual.getUsuario();
            Optional<Documento> documentoOpt = documentoService.findByIdAndUsuario(id, usuario);

            if (documentoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Documento doc = documentoOpt.get();
            List<Map<String, Object>> assinaturasList = new ArrayList<>();
            if (doc.getAssinaturas() != null) {
                for (var assinatura : doc.getAssinaturas()) {
                    assinaturasList.add(Map.of(
                            "id", assinatura.getId(),
                            "usuarioId", assinatura.getUsuario() != null ? assinatura.getUsuario().getId() : 0,
                            "dataCriacao", assinatura.getDataCriacao() != null ? assinatura.getDataCriacao() : "",
                            "dataAssinatura",
                            assinatura.getDataAssinatura() != null ? assinatura.getDataAssinatura() : "",
                            "status", assinatura.getStatus() != null ? assinatura.getStatus().name() : "DESCONHECIDO"));
                }
            }

            Map<String, Object> documentoMap = Map.of(
                    "id", doc.getId(),
                    "titulo", doc.getTitulo() != null ? doc.getTitulo() : "",
                    "conteudo", doc.getConteudo() != null ? doc.getConteudo() : "",
                    "dataCriacao", doc.getDataCriacao() != null ? doc.getDataCriacao() : "",
                    "dataExpiracao", doc.getDataExpiracao() != null ? doc.getDataExpiracao() : "",
                    "status", doc.getStatus() != null ? doc.getStatus().name() : "DESCONHECIDO",
                    "assinaturas", assinaturasList);

            return ResponseEntity.ok(documentoMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Map<String, Object>>> listarDocumentosComFiltros(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false, defaultValue = "TODOS") String status,
            HttpSession session) {
        try {
            Perfil perfilAtual = (Perfil) session.getAttribute("perfilSelecionado");
            if (perfilAtual == null) {
                return ResponseEntity.ok(List.of());
            }

            Usuario usuario = perfilAtual.getUsuario();
            List<Documento> documentos = documentoService.listarDocumentosComFiltros(usuario, busca, status);

            List<Map<String, Object>> documentosMap = new ArrayList<>();

            for (Documento doc : documentos) {
                documentosMap.add(Map.of(
                        "id", doc.getId(),
                        "titulo", doc.getTitulo() != null ? doc.getTitulo() : "",
                        "conteudo", doc.getConteudo() != null ? doc.getConteudo() : "",
                        "dataCriacao", doc.getDataCriacao() != null ? doc.getDataCriacao() : "",
                        "dataExpiracao", doc.getDataExpiracao() != null ? doc.getDataExpiracao() : "",
                        "status", doc.getStatus() != null ? doc.getStatus().name() : "DESCONHECIDO",
                        "assinaturas", doc.getAssinaturas() != null ? doc.getAssinaturas().stream().map(assinatura -> {
                            return Map.of(
                                    "id", assinatura.getId(),
                                    "usuarioId", assinatura.getUsuario() != null ? assinatura.getUsuario().getId() : 0,
                                    "dataCriacao",
                                    assinatura.getDataCriacao() != null ? assinatura.getDataCriacao() : "",
                                    "dataAssinatura",
                                    assinatura.getDataAssinatura() != null ? assinatura.getDataAssinatura() : "",
                                    "status",
                                    assinatura.getStatus() != null ? assinatura.getStatus().name() : "DESCONHECIDO");
                        }).toList() : List.of()));
            }

            return ResponseEntity.ok(documentosMap);
        } catch (Exception e) {
            return ResponseEntity.ok(List.of());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarDocumento(
            @PathVariable Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam(value = "dataExpiracao", required = false) Long dataExpiracaoMs,
            @RequestParam(value = "arquivado", required = false, defaultValue = "false") Boolean arquivado,
            HttpSession session) {
        try {
            Documento doc = documentoService.atualizarDocumento(
                    id,
                    titulo,
                    dataExpiracaoMs,
                    arquivado);

            List<Map<String, Object>> assinaturasList = new ArrayList<>();
            if (doc.getAssinaturas() != null) {
                for (var assinatura : doc.getAssinaturas()) {
                    assinaturasList.add(Map.of(
                            "id", assinatura.getId(),
                            "usuarioId", assinatura.getUsuario() != null ? assinatura.getUsuario().getId() : 0,
                            "dataCriacao", assinatura.getDataCriacao() != null ? assinatura.getDataCriacao() : "",
                            "dataAssinatura",
                            assinatura.getDataAssinatura() != null ? assinatura.getDataAssinatura() : "",
                            "status", assinatura.getStatus() != null ? assinatura.getStatus().name() : "DESCONHECIDO"));
                }
            }

            Map<String, Object> documentoMap = Map.of(
                    "id", doc.getId(),
                    "titulo", doc.getTitulo() != null ? doc.getTitulo() : "",
                    "conteudo", doc.getConteudo() != null ? doc.getConteudo() : "",
                    "dataCriacao", doc.getDataCriacao() != null ? doc.getDataCriacao() : "",
                    "dataExpiracao", doc.getDataExpiracao() != null ? doc.getDataExpiracao() : "",
                    "status", doc.getStatus() != null ? doc.getStatus().name() : "DESCONHECIDO",
                    "assinaturas", assinaturasList);

            return ResponseEntity.ok(documentoMap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocumento(
            @RequestParam("conteudo") MultipartFile arquivo,
            @RequestParam("titulo") String titulo,
            @RequestParam(value = "dataExpiracao", required = false) Long dataExpiracaoMs,
            @RequestParam("usuariosIds") String usuariosIds,
            HttpSession session) {

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeArquivo = UUID.randomUUID().toString() + extensao;
            Path filePath = uploadPath.resolve(nomeArquivo);
            Files.copy(arquivo.getInputStream(), filePath);

            // Gera o link do arquivo
            String linkArquivo = "/uploads/documentos/" + nomeArquivo;

            // Converte data de expiração se fornecida
            Date dataExpiracao = (dataExpiracaoMs != null) ? new Date(dataExpiracaoMs) : null;

            Perfil perfilAtual = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario criador = perfilAtual.getUsuario();

            List<Long> idsUsuarios = Arrays.stream(usuariosIds.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Cria o documento e preenche as propriedades
            Documento documento = documentoService.criarDocumento(
                    titulo,
                    linkArquivo,
                    criador,
                    dataExpiracao,
                    idsUsuarios);

            return ResponseEntity.ok(documento);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao fazer upload do arquivo: " + e.getMessage());
        }
    }
}