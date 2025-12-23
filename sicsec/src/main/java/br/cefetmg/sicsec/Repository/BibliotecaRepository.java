package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;

@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {
}