/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Repository.Usuarios;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
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
public interface AlunoRepo extends JpaRepository<Aluno, Long> {
    
    List<Aluno> findByCargo(Cargo cargo);

    Optional<Aluno> findByMatricula_NumeroMatricula(Long matricula);

    @Query("""
            SELECT u
            FROM Usuario u
            JOIN u.matricula m
            WHERE m.cpf.cpf = :cpf
            """)
    List<Aluno> findByCpf(@Param("cpf") Long cpf);
    
    List<Aluno> findByMatricula_NomeContaining(String nome);

     @Query("""
            SELECT a
            FROM Aluno a
            WHERE a.curso.id = :cursoId
            """)
    List<Aluno> findAllByCurso(@Param("cursoId") Long cursoId);

}