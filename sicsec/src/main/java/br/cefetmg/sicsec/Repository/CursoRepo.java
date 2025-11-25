/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Repository;

import br.cefetmg.sicsec.Model.Curso.Curso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author davig
 */
@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {
    

    @Query("""
            SELECT c
            FROM Curso c
            WHERE c.id = :departamento_id
            """)
    List<Curso> findByDepartamentoId(@Param("departamento_id") Long departamentoId);

    @Query("""
            SELECT c
            FROM Curso c
            JOIN c.matrizCurricular mc
            WHERE mc.id = :disciplina_id
            """)
    Curso findByDisciplina(@Param("disciplina_id") Long disciplinaId);

}