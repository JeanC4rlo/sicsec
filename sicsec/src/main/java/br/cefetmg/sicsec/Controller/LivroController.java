package br.cefetmg.sicsec.Controller;

import org.springframework.web.bind.annotation.*;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;
import br.cefetmg.sicsec.Model.Biblioteca.Livro;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Bibliotecario.Bibliotecario;
import br.cefetmg.sicsec.Model.Util.Enum.StatusLivro;
import br.cefetmg.sicsec.Service.BibliotecarioService;
import br.cefetmg.sicsec.Service.LivroService;
import br.cefetmg.sicsec.dto.Perfil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    BibliotecarioService bibliotecarioService;

    @Autowired
    LivroService livroService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarLivroComBiblioteca(
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam String editora,
            @RequestParam Long isbn,
            @RequestParam Integer ano,
            HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario usuario = perfil.getUsuario();
            Long idUsuario = usuario.getId();

            Optional<Bibliotecario> bibliotecarioOpt = bibliotecarioService.findById(idUsuario);
            Bibliotecario bibliotecario;

            if (!bibliotecarioOpt.isEmpty())
                bibliotecario = bibliotecarioOpt.get();
            else
                throw new EntityNotFoundException("O bibliotecário não foi encontrado nos registros");

            Biblioteca biblioteca = bibliotecario.getBiblioteca();

            Livro livro = livroService.criarLivro(
                    titulo,
                    autor,
                    editora,
                    isbn,
                    ano,
                    biblioteca);

            return ResponseEntity.ok(livro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar o livro: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarLivros(
        @RequestParam(required = false) String busca, 
        @RequestParam(required = false, defaultValue = "TODOS") String status,
        HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario usuario = perfil.getUsuario();
            Long idUsuario = usuario.getId();

            Bibliotecario bibliotecario = bibliotecarioService.findById(idUsuario)
                    .orElseThrow(() -> new EntityNotFoundException("O bibliotecário não foi encontrado"));

            Biblioteca biblioteca = bibliotecario.getBiblioteca();

            if(!"TODOS".equals(status)) {
                StatusLivro statusEnum = StatusLivro.valueOf(status.toUpperCase());
                return ResponseEntity.ok(livroService.listarLivrosPorBibliotecaEStatus(busca, biblioteca, statusEnum));
            }

            return ResponseEntity.ok(livroService.listarLivrosPorBiblioteca(busca, biblioteca));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao listar livros: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterLivro(@PathVariable Long id, HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario usuario = perfil.getUsuario();
            Long idUsuario = usuario.getId();

            Bibliotecario bibliotecario = bibliotecarioService.findById(idUsuario)
                    .orElseThrow(() -> new EntityNotFoundException("O bibliotecário não foi encontrado"));

            Livro livro = livroService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

            boolean pertence = livro.getBibliotecas().stream()
                    .anyMatch(b -> b.getId().equals(bibliotecario.getBiblioteca().getId()));

            if (!pertence) {
                return ResponseEntity.status(403).body("Livro não pertence à sua biblioteca");
            }

            return ResponseEntity.ok(livro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao obter livro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarLivro(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam String editora,
            @RequestParam Long isbn,
            @RequestParam Integer ano,
            @RequestParam Boolean disponivel,
            HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario usuario = perfil.getUsuario();
            Long idUsuario = usuario.getId();

            Bibliotecario bibliotecario = bibliotecarioService.findById(idUsuario)
                    .orElseThrow(() -> new EntityNotFoundException("O bibliotecário não foi encontrado"));

            Livro livro = livroService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

            boolean pertence = livro.getBibliotecas().stream()
                    .anyMatch(b -> b.getId().equals(bibliotecario.getBiblioteca().getId()));

            if (!pertence) {
                return ResponseEntity.status(403).body("Livro não pertence à sua biblioteca");
            }

            if(livro.getStatus().equals(StatusLivro.EMPRESTADO) && !disponivel) {
                return ResponseEntity.status(403).body("Livros emprestados não podem estar indisponíveis");
            }

            StatusLivro status;
            if(disponivel)
                status = StatusLivro.DISPONIVEL;
            else
                status = StatusLivro.INDISPONIVEL;

            livroService.atualizarLivro(livro, titulo, autor, editora, isbn, ano, status);

            return ResponseEntity.ok(livro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar livro: " + e.getMessage());
        }
    }
}