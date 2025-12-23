package br.cefetmg.sicsec.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;
import br.cefetmg.sicsec.Model.Biblioteca.Livro;
import br.cefetmg.sicsec.Model.Util.Enum.StatusLivro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByCodigo(Long codigo);

    @Query("""
        SELECT l FROM Livro l
        WHERE l.titulo = :titulo
            AND l.autor = :autor
            AND l.editora = :editora
            AND l.isbn = :isbn
            AND l.ano = :ano
    """)
    Optional<Livro> findSimilar(
        @Param("titulo") String titulo,
        @Param("autor") String autor,
        @Param("editora") String editora,
        @Param("isbn") Long isbn,
        @Param("ano") Integer ano
    );

    @Query("""
        SELECT l FROM Livro l
        JOIN l.bibliotecas b
        WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND b = :biblioteca
    """)
    List<Livro> findAllByTituloBiblioteca(
        @Param("titulo") String titulo,
        @Param("biblioteca") Biblioteca biblioteca
    );

    @Query("""
        SELECT l FROM Livro l
        JOIN l.bibliotecas b
        WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))
            AND b = :biblioteca
            AND l.status = :status
    """)
    List<Livro> findAllByTituloBibliotecaStatus(
        @Param("titulo") String titulo,
        @Param("biblioteca") Biblioteca biblioteca,
        @Param("status") StatusLivro status
    );

    @Query("""
        SELECT l FROM Livro l
        JOIN l.bibliotecas b
        WHERE l.codigo = :codigo
            AND b = :biblioteca
    """)
    Optional<Livro> findByCodigoAndBiblioteca(
        @Param("codigo") Long codigo,
        @Param("biblioteca") Biblioteca biblioteca
    );
}