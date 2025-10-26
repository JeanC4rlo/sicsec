/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta;

/**
 *
 * @author davig
 */
public class QuestaoAbertaModel implements IQuestao {
    
    private String enunciado;
    private double notaTotal;
    
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
