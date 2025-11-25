package br.cefetmg.sicsec.Repository.Usuarios;

import br.cefetmg.sicsec.Model.Usuario.Administrador.*;
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
public interface AdministradorRepo extends JpaRepository<Administrador, Long> {
    //Busca usuários por tipo de matrícula (ESTUDANTE, PROFESSOR, ADMIN)
    List<Administrador> findByCargo(Cargo cargo);

    //Busca usuários pela matrícula
    Optional<Administrador> findByMatricula_NumeroMatricula(Long matricula);

    //Busca usuários pelo CPF
    @Query("""
            SELECT u
            FROM Administrador u
            JOIN u.matricula m
            WHERE m.cpf.cpf = :cpf
            """)
    List<Administrador> findByCpf(@Param("cpf") Long cpf);
    
    //Busca usuários pelo nome contendo algo (LIKE %nome%)
    List<Administrador> findByMatricula_NomeContaining(String nome);

}