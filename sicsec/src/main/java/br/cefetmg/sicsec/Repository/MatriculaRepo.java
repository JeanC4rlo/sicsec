/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.cefetmg.sicsec.Model.Usuario.Matricula;

/**
 *
 * @author davig
 */
@Repository
public interface MatriculaRepo extends JpaRepository<Matricula, Long> {
    
    @Query("""
            SELECT m
            FROM Matricula m
            WHERE m.cpf.cpf = :cpf
            """)
    Matricula findByCpf(@Param("cpf") Long cpf);

    boolean existsByNumeroMatricula(Long numeroMatricula);

    Matricula findByNumeroMatricula(Long numeroMatricula);
    
}
