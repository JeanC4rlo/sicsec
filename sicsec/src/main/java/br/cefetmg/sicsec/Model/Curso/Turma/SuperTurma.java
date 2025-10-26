/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

import java.util.List;

/**
 *
 * @author davig
 */
public class SuperTurma extends Turma {

    private List<SubTurma> subTurmas;

    public List<SubTurma> getSubTurmas() {
        return subTurmas;
    }

    public void setSubTurmas(List<SubTurma> subTurmas) {
        this.subTurmas = subTurmas;
    }
    
}
