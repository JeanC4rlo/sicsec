package br.cefetmg.sicsec.Service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;
import br.cefetmg.sicsec.Model.Biblioteca.Livro;
import br.cefetmg.sicsec.Model.Util.Enum.StatusLivro;
import br.cefetmg.sicsec.Repository.BibliotecaRepository;
import br.cefetmg.sicsec.Repository.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Transactional
    public Livro criarLivro(
            String titulo,
            String autor,
            String editora,
            Long isbn,
            Integer ano,
            Biblioteca biblioteca) {

        Optional<Livro> livroOptional = livroRepository.findSimilar(titulo, autor, editora, isbn, ano);
        Livro livro;

        if (livroOptional.isEmpty()) {
            livro = new Livro();
            livro.setTitulo(titulo);
            livro.setAutor(autor);
            livro.setEditora(editora);
            livro.setIsbn(isbn);
            livro.setAno(ano);
            livro.setStatus(StatusLivro.DISPONIVEL);

            Long codigo = gerarCodigoLivro(titulo, autor, editora, ano);
            livro.setCodigo(codigo);

            livro.setBibliotecas(new ArrayList<>());
        } else {
            livro = livroOptional.get();
        }

        if (!livro.getBibliotecas().contains(biblioteca)) {
            livro.getBibliotecas().add(biblioteca);
        }

        Livro livroRetornado = livroRepository.save(livro);

        if(!biblioteca.getAcervo().contains(livroRetornado)) {
            biblioteca.getAcervo().add(livroRetornado);
        }

        bibliotecaRepository.save(biblioteca);

        return livroRetornado;
    }

    private Long gerarCodigoLivro(String titulo, String autor, String editora, Integer ano) {
        int hash = Math.abs((titulo + autor + editora + ano).hashCode()) % (100_000_000);

        String codigoBase = String.format("1%08d", hash);

        Long codigoLong = Long.parseLong(codigoBase);
        while (livroRepository.existsByCodigo(codigoLong)) {
            hash = (hash + 1) % (10 * 8);
            codigoLong = Long.parseLong(String.format("%s%05d", ano, hash));
        }

        return codigoLong;
    }

	@Transactional
    public Livro atualizarLivro(
            Livro livro,
            String titulo,
            String autor,
            String editora,
            Long isbn,
            Integer ano,
            StatusLivro status    
        ) {

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setIsbn(isbn);
        livro.setAno(ano);
        livro.setStatus(status);

        return livroRepository.save(livro);
    }

	public Optional<Livro> findById(Long id) {
        return livroRepository.findById(id);
    }

    public Optional<Livro> findByCodigoAndBiblioteca(Long codigo, Biblioteca biblioteca) {
        return livroRepository.findByCodigoAndBiblioteca(codigo, biblioteca);
    }

	public Object listarLivrosPorBiblioteca(String busca, Biblioteca biblioteca) {
		return livroRepository.findAllByTituloBiblioteca(busca, biblioteca);
	}

    public Object listarLivrosPorBibliotecaEStatus(String busca, Biblioteca biblioteca, StatusLivro status) {
		return livroRepository.findAllByTituloBibliotecaStatus(busca, biblioteca, status);
	}

    public Livro save(Livro livro) {
        return livroRepository.save(livro);
    }
}
