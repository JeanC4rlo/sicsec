package br.cefetmg.sicsec.Repository.Usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.cefetmg.sicsec.Model.Usuario.Bibliotecario.Bibliotecario;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}
