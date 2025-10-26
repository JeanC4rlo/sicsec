/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

/**
 *
 * @author davig
 */
public class SubTurma extends Turma {
    
    private SuperTurma superTurma;

    public SuperTurma getSuperTurma() {
        return superTurma;
    }

    public void setSuperTurma(SuperTurma superTurma) {
        this.superTurma = superTurma;
    }

}