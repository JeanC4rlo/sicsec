package br.cefetmg.sicsec.Controller;

import org.springframework.web.bind.annotation.*;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;
import br.cefetmg.sicsec.Model.Biblioteca.Emprestimo;
import br.cefetmg.sicsec.Model.Biblioteca.Livro;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Bibliotecario.Bibliotecario;
import br.cefetmg.sicsec.Model.Util.Enum.Reserva;
import br.cefetmg.sicsec.Model.Util.Enum.StatusLivro;
import br.cefetmg.sicsec.Service.BibliotecarioService;
import br.cefetmg.sicsec.Service.EmprestimoService;
import br.cefetmg.sicsec.Service.LivroService;
import br.cefetmg.sicsec.Service.UsuarioService;
import br.cefetmg.sicsec.dto.EmprestimoRespostaDTO;
import br.cefetmg.sicsec.dto.LivroResumoDTO;
import br.cefetmg.sicsec.dto.Perfil;
import br.cefetmg.sicsec.dto.UsuarioResumoDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    BibliotecarioService bibliotecarioService;

    @Autowired
    LivroService livroService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarEmprestimo(
            @RequestParam String codigo,
            @RequestParam String tipo,
            @RequestParam Integer duracao,
            @RequestParam Long matricula,
            HttpSession session) {

        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario bibliotecarioUsuario = perfil.getUsuario();

            Bibliotecario bibliotecario = bibliotecarioService.findById(bibliotecarioUsuario.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Bibliotecário não encontrado"));

            Biblioteca biblioteca = bibliotecario.getBiblioteca();

            Long codigoLong = Long.parseLong(codigo);
            Livro livro = livroService
                    .findByCodigoAndBiblioteca(codigoLong, biblioteca)
                    .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado ou não pertence a esta biblioteca"));

            if (livro.getStatus() == StatusLivro.INDISPONIVEL) {
                return ResponseEntity.badRequest().body("Livro indisponível para empréstimo");
            }

            Usuario usuario = usuarioService.findByNumeroMatricula(matricula)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            
            Reserva reservaEnum;
            try {
                reservaEnum = Reserva.valueOf(tipo.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Tipo de empréstimo inválido");
            }

            Emprestimo emprestimo = emprestimoService.criarEmprestimo(
                    livro,
                    usuario,
                    duracao,
                    reservaEnum,
                    new Date());

            if (reservaEnum == Reserva.EMPRESTIMO) {
                livro.setStatus(StatusLivro.EMPRESTADO);
                livroService.save(livro);
            }

            return ResponseEntity.ok(emprestimo);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Código do livro inválido");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao criar empréstimo");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarEmprestimos(
            @RequestParam(required = false) String matricula,
            @RequestParam(required = false, defaultValue = "TODOS") String status,
            HttpSession session) {
        
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario bibliotecarioUsuario = perfil.getUsuario();

            Bibliotecario bibliotecario = bibliotecarioService.findById(bibliotecarioUsuario.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Bibliotecário não encontrado"));

            Biblioteca biblioteca = bibliotecario.getBiblioteca();
            
            Long matriculaLong = null;
            if (matricula != null && !matricula.trim().isEmpty()) {
                try {
                    matriculaLong = Long.parseLong(matricula);
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("Matrícula inválida");
                }
            }

            List<Emprestimo> emprestimos;
            if (matriculaLong != null) {
                Usuario usuario = usuarioService.findByNumeroMatricula(matriculaLong)
                        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
                
                emprestimos = emprestimoService.findByUsuario(usuario);
            } else {
                emprestimos = emprestimoService.findByBiblioteca(biblioteca);
            }

            if (!"TODOS".equals(status)) {
                try {
                    Reserva reservaStatus = Reserva.valueOf(status.toUpperCase());
                    emprestimos = emprestimos.stream()
                            .filter(e -> e.getReserva() == reservaStatus)
                            .collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("Status inválido");
                }
            }

            List<EmprestimoRespostaDTO> resposta = emprestimos.stream()
                    .map(this::converterParaDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(resposta);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao listar empréstimos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterEmprestimo(@PathVariable Long id, HttpSession session) {
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario bibliotecarioUsuario = perfil.getUsuario();

            Bibliotecario bibliotecario = bibliotecarioService.findById(bibliotecarioUsuario.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Bibliotecário não encontrado"));

            Emprestimo emprestimo = emprestimoService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

            boolean pertence = emprestimo.getLivro().getBibliotecas().stream()
                    .anyMatch(b -> b.getId().equals(bibliotecario.getBiblioteca().getId()));

            if (!pertence) {
                return ResponseEntity.status(403).body("Empréstimo não pertence à sua biblioteca");
            }

            return ResponseEntity.ok(converterParaDTO(emprestimo));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao obter empréstimo");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEmprestimo(
            @PathVariable Long id,
            @RequestParam Integer duracao,
            @RequestParam Boolean historico,
            @RequestParam Long matricula,
            HttpSession session) {
        
        try {
            Perfil perfil = (Perfil) session.getAttribute("perfilSelecionado");
            Usuario bibliotecarioUsuario = perfil.getUsuario();

            Bibliotecario bibliotecario = bibliotecarioService.findById(bibliotecarioUsuario.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Bibliotecário não encontrado"));

            Emprestimo emprestimo = emprestimoService.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

            boolean pertence = emprestimo.getLivro().getBibliotecas().stream()
                    .anyMatch(b -> b.getId().equals(bibliotecario.getBiblioteca().getId()));

            if (!pertence) {
                return ResponseEntity.status(403).body("Empréstimo não pertence à sua biblioteca");
            }

            if (duracao == null || duracao < 1) {
                return ResponseEntity.badRequest().body("Duração inválida");
            }

            Livro livro = emprestimo.getLivro();
            Reserva statusAnterior = emprestimo.getReserva();
            System.out.println(historico);
            Reserva statusNovo = historico ? Reserva.HISTORICO : statusAnterior;
            
            if (statusAnterior == Reserva.EMPRESTIMO && statusNovo != Reserva.EMPRESTIMO) {
                livro.setStatus(StatusLivro.DISPONIVEL);
            } else if (statusAnterior != Reserva.EMPRESTIMO && statusNovo == Reserva.EMPRESTIMO) {
                if (livro.getStatus() == StatusLivro.DISPONIVEL) {
                    livro.setStatus(StatusLivro.EMPRESTADO);
                } else {
                    return ResponseEntity.badRequest().body("Livro não está disponível para empréstimo");
                }
            }

            Usuario novoUsuario = usuarioService.findByNumeroMatricula(matricula)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            
            livroService.save(livro);

            emprestimo.setDuracao(duracao);
            emprestimo.setReserva(statusNovo);
            emprestimo.setData(new Date());
            emprestimo.setMortuario(novoUsuario);
            
            emprestimoService.save(emprestimo);

            return ResponseEntity.ok(emprestimo);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao atualizar empréstimo");
        }
    }

    private EmprestimoRespostaDTO converterParaDTO(Emprestimo emprestimo) {
        EmprestimoRespostaDTO dto = new EmprestimoRespostaDTO();
        dto.setId(emprestimo.getId());
        dto.setDuracao(emprestimo.getDuracao());
        dto.setData(emprestimo.getData());
        dto.setReserva(emprestimo.getReserva().toString());
        
        LivroResumoDTO livroDTO = new LivroResumoDTO();
        livroDTO.setId(emprestimo.getLivro().getId());
        livroDTO.setTitulo(emprestimo.getLivro().getTitulo());
        livroDTO.setCodigo(String.valueOf(emprestimo.getLivro().getCodigo()));
        dto.setLivro(livroDTO);
        
        UsuarioResumoDTO usuarioDTO = new UsuarioResumoDTO();
        usuarioDTO.setId(emprestimo.getMortuario().getId());
        usuarioDTO.setNome(emprestimo.getMortuario().getMatricula().getNome());
        usuarioDTO.setMatricula(emprestimo.getMortuario().getMatricula().getNumeroMatricula());
        dto.setUsuario(usuarioDTO);

        return dto;
    }
}