package br.cefetmg.sicsec.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;
import br.cefetmg.sicsec.Model.Biblioteca.Emprestimo;
import br.cefetmg.sicsec.Model.Biblioteca.Livro;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.Reserva;
import br.cefetmg.sicsec.Model.Util.Enum.StatusLivro;
import br.cefetmg.sicsec.Repository.EmprestimoRepository;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    LivroService livroService;

    @Transactional
    public Emprestimo criarEmprestimo(Livro livro, Usuario usuario, Integer duracao, 
                                     Reserva reservaEnum, Date dataEmprestimo) {

        if (livro == null) {
            throw new IllegalArgumentException("Livro não informado");
        }

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não informado");
        }

        if (duracao == null || duracao <= 0) {
            throw new IllegalArgumentException("Duração inválida");
        }

        if (reservaEnum == null) {
            throw new IllegalArgumentException("Tipo de reserva inválido");
        }

        StatusLivro statusLivro = livro.getStatus();

        if (statusLivro == StatusLivro.INDISPONIVEL) {
            throw new IllegalStateException("Livro indisponível");
        }

        if (statusLivro == StatusLivro.EMPRESTADO) {
            if (reservaEnum == Reserva.EMPRESTIMO) {
                throw new IllegalStateException("Livro já está emprestado");
            }
            if (reservaEnum == Reserva.RESERVA) {
                throw new IllegalStateException("Livro já está emprestado, não pode ser reservado");
            }
        }

        if (statusLivro == StatusLivro.DISPONIVEL && reservaEnum == Reserva.RESERVA) {
            throw new IllegalStateException("Livro disponível, faça um empréstimo direto");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setMortuario(usuario);
        emprestimo.setDuracao(duracao);
        emprestimo.setReserva(reservaEnum);
        emprestimo.setData(dataEmprestimo != null ? dataEmprestimo : new Date());

        return emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> findByUsuario(Usuario usuario) {
        return emprestimoRepository.findByMortuario(usuario);
    }

    public List<Emprestimo> findByBiblioteca(Biblioteca biblioteca) {
        return emprestimoRepository.findByLivroBibliotecas(biblioteca);
    }

    public Optional<Emprestimo> findById(Long id) {
        return emprestimoRepository.findById(id);
    }

    @Transactional
    public Emprestimo save(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }
}