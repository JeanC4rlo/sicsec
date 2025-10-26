/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.presenca;

import br.cefetmg.sicsec.Model.Usuario.Aluno.AlunoModel;

/**
 *
 * @author davig
 */
public class PresencaModel {
    
    private AlunoModel discente;
    private boolean presente;

    public AlunoModel getDiscente() {
        return discente;
    }

    public void setDiscente(AlunoModel discente) {
        this.discente = discente;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
    
}
