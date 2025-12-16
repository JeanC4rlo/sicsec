package br.cefetmg.sicsec.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.cefetmg.sicsec.Model.Arquivo;
import br.cefetmg.sicsec.Service.ArquivoService;

@RestController
@RequestMapping("/api/arquivo")

public class ArquivosController {
    @Autowired
    ArquivoService arquivoService;

    @GetMapping("/listar/{tipoDono}/{donoId}")
    public ResponseEntity<List<Arquivo>> listarArquivos(@PathVariable String tipoDono, @PathVariable Long donoId) {
        List<Arquivo> arquivos = arquivoService.listarArquivos(tipoDono, donoId);
        return ResponseEntity.ok(arquivos);
    }

    @GetMapping("{arquivoId}/{tipoDono}/{donoId}")
    public ResponseEntity<Resource> baixarArquivo(
            @PathVariable String tipoDono,
            @PathVariable Long donoId,
            @PathVariable Long arquivoId) throws IOException {

        Arquivo arquivo = arquivoService.getArquivo(arquivoId);
        String nomeArquivo = arquivo.getNomeSalvo();

        Path caminho = Paths.get("uploads/" + tipoDono + "-" + donoId)
                .resolve(nomeArquivo)
                .normalize();

        if (!Files.exists(caminho)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(caminho.toUri());

        String contentType = Files.probeContentType(caminho);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}
