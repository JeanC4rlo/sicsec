/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Turma;

/**
 *
 * @author davig
 */
public class SubTurmaModel extends TurmaModel {
    
    private TurmaModel superTurma;

    public TurmaModel getSuperTurma() {
        return superTurma;
    }

    public void setSuperTurma(TurmaModel superTurma) {
        this.superTurma = superTurma;
    }
    
}
