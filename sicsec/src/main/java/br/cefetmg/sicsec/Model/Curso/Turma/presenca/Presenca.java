/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.presenca;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;

/**
 *
 * @author davig
 */
public class Presenca {
    
    private Aluno discente;
    private boolean presente;

    public Aluno getDiscente() {
        return discente;
    }

    public void setDiscente(Aluno discente) {
        this.discente = discente;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
    
}
