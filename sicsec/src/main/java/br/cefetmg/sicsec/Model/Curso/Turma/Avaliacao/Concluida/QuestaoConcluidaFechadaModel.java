/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Concluida;

import br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta.QuestaoFechadaModel;

/**
 *
 * @author davig
 */
public class QuestaoConcluidaFechadaModel extends QuestaoFechadaModel implements IQuestaoConcluida {
    
    private int alternativaEscolhida;
    private String comentario;
    private double notaObtida;

    public int getAlternativaEscolhida() {
        return alternativaEscolhida;
    }

    public void setAlternativaEscolhida(int alternativaEscolhida) {
        this.alternativaEscolhida = alternativaEscolhida;
    }
    
    @Override
    public double getNotaObtida() {
        return notaObtida;
    }
    
    @Override
    public void setNotaObtida(double notaObtida) {
        this.notaObtida = notaObtida;
    }

    @Override
    public String getComentario() {
        return comentario;
    }

    @Override
    public void setComentario(String comentarioDoscente) {
        this.comentario = comentarioDoscente;
    }
}
