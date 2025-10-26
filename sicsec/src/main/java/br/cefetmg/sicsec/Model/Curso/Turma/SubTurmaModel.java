/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

/**
 *
 * @author davig
 */
public class SubTurmaModel extends TurmaModel {
    
    private SuperTurmaModel superTurma;

    public SuperTurmaModel getSuperTurma() {
        return superTurma;
    }

    public void setSuperTurma(SuperTurmaModel superTurma) {
        this.superTurma = superTurma;
    }

}