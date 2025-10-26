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
public class SuperTurmaModel extends TurmaModel {

    private List<SubTurmaModel> subTurmas;

    public List<SubTurmaModel> getSubTurmas() {
        return subTurmas;
    }

    public void setSubTurmas(List<SubTurmaModel> subTurmas) {
        this.subTurmas = subTurmas;
    }
    
}
