package br.cefetmg.sicsec.Controller;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;

public class ValidacaoDeArquivosHelper {

    private static final Set<String> EXTENSOES_PERMITIDAS = Set.of(".txt", ".pdf", ".docx", ".zip");
    private static final long TAMANHO_MAX = 5 * 1024 * 1024;

    public static boolean validar(MultipartFile file) throws IOException {
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path temp = Files.createTempFile("upload_", "_" + limparNome(file.getOriginalFilename()));
        Files.write(temp, file.getBytes());

        try {
            String tipoMime = Files.probeContentType(temp);
            if (tipoMime == null)
                tipoMime = "";
            String nomeArquivo = file.getOriginalFilename().toLowerCase();
            boolean extensaoValida = EXTENSOES_PERMITIDAS.stream().anyMatch(nomeArquivo::endsWith);
            if (!extensaoValida || tipoMime.equals("application/x-msdownload")) {
                return false;
            }

            if (Files.size(temp) > TAMANHO_MAX) {
                return false;
            }

            Path destino = uploadDir.resolve(limparNome(file.getOriginalFilename()));
            Files.copy(temp, destino, StandardCopyOption.REPLACE_EXISTING);

            return true;
        } finally {
            Files.deleteIfExists(temp);
        }
    }
    private static String limparNome(String nome) {
        if (nome == null)
            return "arquivo_sem_nome";
        return Paths.get(nome).getFileName().toString().replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
