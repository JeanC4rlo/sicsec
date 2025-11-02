/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Repository;

import br.cefetmg.sicsec.Model.Curso.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author davig
 */
@Repository
public interface DisciplinaRepo extends JpaRepository<Disciplina, Long> {
    
}
