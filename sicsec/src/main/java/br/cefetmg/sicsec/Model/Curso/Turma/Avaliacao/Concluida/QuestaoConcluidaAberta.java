/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Concluida;

import br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta.QuestaoAberta;

/**
 *
 * @author davig
 */
public class QuestaoConcluidaAberta extends QuestaoAberta implements IQuestaoConcluida {
    
    private String dissertacao;
    private String comentario;
    private double notaObtida;

    public String getDissertacao() {
        return dissertacao;
    }

    public void setDissertacao(String dissertacao) {
        this.dissertacao = dissertacao;
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
