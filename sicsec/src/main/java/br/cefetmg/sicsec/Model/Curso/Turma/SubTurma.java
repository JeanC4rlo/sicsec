/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

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