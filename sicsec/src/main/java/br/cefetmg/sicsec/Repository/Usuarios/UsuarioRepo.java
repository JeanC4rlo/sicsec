/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Repository.Usuarios;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
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
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {
    //Busca usuários por tipo de matrícula (ESTUDANTE, PROFESSOR, ADMIN)
    List<Usuario> findByCargo(Cargo cargo);

    //Busca usuários pela matrícula
    Optional<Usuario> findByMatricula_NumeroMatricula(Long matricula);

    //Busca usuários pelo CPF
    @Query("""
            SELECT u
            FROM Usuario u
            JOIN u.matricula m
            WHERE m.cpf.cpf = :cpf
            """)
    Usuario findByCpf(@Param("cpf") Long cpf);
    
    //Busca usuários pelo nome contendo algo (LIKE %nome%)
    List<Usuario> findByMatricula_NomeContaining(String nome);

    Optional<Usuario> findById(Long id);
}