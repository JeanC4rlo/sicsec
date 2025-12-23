package br.cefetmg.sicsec.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;
import br.cefetmg.sicsec.Model.Biblioteca.Emprestimo;
import br.cefetmg.sicsec.Model.Usuario.Usuario;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    
    List<Emprestimo> findByMortuario(Usuario usuario);
    
    @Query("SELECT e FROM Emprestimo e WHERE e.livro IN " +
           "(SELECT l FROM Livro l JOIN l.bibliotecas b WHERE b = :biblioteca)")
    List<Emprestimo> findByLivroBibliotecas(@Param("biblioteca") Biblioteca biblioteca);
}