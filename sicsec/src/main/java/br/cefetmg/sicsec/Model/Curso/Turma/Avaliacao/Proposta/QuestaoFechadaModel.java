/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta;

import java.util.List;

/**
 *
 * @author davig
 */
public class QuestaoFechadaModel implements IQuestao {
    
    private List<String> alternativas;
    private int gabarito;
    private String enunciado;
    private double notaTotal;

    public List<String> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<String> alternativas) {
        this.alternativas = alternativas;
    }

    public int getGabarito() {
        return gabarito;
    }

    public void setGabarito(int gabarito) {
        this.gabarito = gabarito;
    }
    
    @Override
    public String getEnunciado() {
        return enunciado;
    }
    
    @Override
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    
    @Override
    public double getNotaTotal() {
        return notaTotal;
    }
    
    @Override
    public void setNotaTotal(double notaTotal) {
        this.notaTotal = notaTotal;
    }
}
