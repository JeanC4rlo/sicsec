/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta;

import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import java.util.List;

/**
 *
 * @author davig
 */


public class Avaliacao {
 
    private Professor docente;
    private int notaTotal;
    private List<IQuestao> bancoQuestoes;
    private int amostraQuestoes;
    private boolean aberta; //Se está ou não aberta para ser respondida pelos alunos
    private boolean notaAoFim; //Se a nota vai ser disponibilizada automaticamente quando o aluno terminar a avaliação;
                               //Todas questões precisam ser fechadas para isso

    public Professor getDocente() {
        return docente;
    }

    public void setDocente(Professor docente) {
        this.docente = docente;
    }

    public int getNotaTotal() {
        return notaTotal;
    }

    public void setNotaTotal(int notaTotal) {
        this.notaTotal = notaTotal;
    }

    public List<IQuestao> getBancoQuestoes() {
        return bancoQuestoes;
    }

    public void setBancoQuestoes(List<IQuestao> bancoQuestoes) {
        this.bancoQuestoes = bancoQuestoes;
    }

    public int getAmostraQuestoes() {
        return amostraQuestoes;
    }

    public void setAmostraQuestoes(int amostraQuestoes) {
        this.amostraQuestoes = amostraQuestoes;
    }

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public boolean isNotaAoFim() {
        return notaAoFim;
    }

    public void setNotaAoFim(boolean notaAoFim) {
        this.notaAoFim = notaAoFim;
    }
    
    
    
}