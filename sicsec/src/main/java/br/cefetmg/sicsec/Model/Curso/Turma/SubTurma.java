/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Util.Enum.TipoTurma;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author davig
 */
@Entity
public class SubTurma extends Turma {

    @ManyToOne
    @JoinColumn(name = "super_turma_id", nullable = false)
    @JsonBackReference
    private SuperTurma superTurma;

    
    public SubTurma() {
        
    }
    
    public SubTurma(String nome, Disciplina disciplina, Curso curso, List<Aluno> discentes, List<Professor> doscentes, SuperTurma turma) {
        super(nome, disciplina, curso, discentes, doscentes);
        this.setTipo(TipoTurma.SUBTURMA);
        this.setSuperTurma(turma);
    }
    
    public SubTurma(String nome, Disciplina disciplina, Curso curso, SuperTurma turma) {
        super(nome, disciplina, curso);
        this.setTipo(TipoTurma.SUBTURMA);
        this.setSuperTurma(turma);
    }
    
    public SuperTurma getSuperTurma() {
        return superTurma;
    }

    public void setSuperTurma(SuperTurma superTurma) {
        if (superTurma == null) {
            throw new IllegalArgumentException("Toda SubTurma deve ter uma SuperTurma associada.");
        }
        this.superTurma = superTurma;
    }

}