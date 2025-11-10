package br.cefetmg.sicsec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.cefetmg.sicsec.Model.Usuario.Matricula;
import br.cefetmg.sicsec.Service.MatriculaService;

@Controller
@RequestMapping("/api")
public class MatriculaController {
    @Autowired
    private MatriculaService matriculaService;

    @PostMapping("/matricular")
    public ResponseEntity<String> matricularUsuario(
            @RequestParam String cpf,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam Long cursoId) {

        try {
            matriculaService.matricular(cpf, nome, email, telefone, cursoId);
            return ResponseEntity.ok("Matrícula realizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao realizar matrícula: " + e.getMessage());
        }
    }

    @GetMapping("/matricula/{numeroMatricula}")
    public ResponseEntity<?> buscarMatricula(@PathVariable String numeroMatricula) {
        try {
            Matricula matricula = matriculaService.buscarPorNumero(numeroMatricula);
            if (matricula == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada");
            }
            return ResponseEntity.ok(matricula);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar matrícula: " + e.getMessage());
        }
    }

    @PostMapping("/matricula/atualizar")
    public ResponseEntity<String> atualizarUsuario(@RequestParam String cpf,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String cursoId,
            @RequestParam String numeroMatriculaAnterior,
            @RequestParam String numeroMatriculaNovo) {
        try {
            matriculaService.atualizar(cpf, nome, email, telefone, cursoId, numeroMatriculaAnterior, numeroMatriculaNovo);
            return ResponseEntity.ok("Matrícula atualizada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar matrícula: " + e.getMessage());
        }
    }
}