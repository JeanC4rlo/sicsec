package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Service.ArquivoService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/arquivo")

public class ArquivosController {
    @Autowired
    ArquivoService arquivoService;

    @GetMapping("/download/{nomeArquivo}")
    public ResponseEntity<Resource> baixarArquivo(@PathVariable String nomeArquivo) throws IOException {
        Path caminho = Paths.get("uploads").resolve(nomeArquivo).normalize();

        if (!Files.exists(caminho))
            return ResponseEntity.notFound().build();

        Resource resource = new UrlResource(caminho.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/{nomeArquivo}")
    public ResponseEntity<Resource> getArquvio(@PathVariable String nomeArquivo) throws IOException {
        Path caminho = Paths.get("uploads").resolve(nomeArquivo).normalize();

        if (!Files.exists(caminho))
            return ResponseEntity.notFound().build();

        Resource resource = new UrlResource(caminho.toUri());

        String contentType = Files.probeContentType(caminho);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @GetMapping("/listar/{atividadeId}")
    public ResponseEntity<List<?>> listarArquivos(@PathVariable Long atividadeId) {
        List<?> arquivos = arquivoService.listarArquivos(atividadeId);
        return ResponseEntity.ok(arquivos);
    }

}
