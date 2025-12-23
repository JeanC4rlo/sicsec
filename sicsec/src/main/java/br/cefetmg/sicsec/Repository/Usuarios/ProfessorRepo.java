package br.cefetmg.sicsec.Repository.Usuarios;

import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


/**
 *
 * @author davig
 */

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, Long> {
    
    List<Professor> findByCargo(Cargo cargo);

    Optional<Professor> findByMatricula_NumeroMatricula(Long matricula);

    @Query("""
            SELECT u
            FROM Usuario u
            JOIN u.matricula m
            WHERE m.cpf.cpf = :cpf
            """)
    List<Professor> findByCpf(@Param("cpf") Long cpf);
    
    List<Professor> findByMatricula_NomeContaining(String nome);

    List<Professor> findAllByDepartamentoId(Long departamentoId);

}