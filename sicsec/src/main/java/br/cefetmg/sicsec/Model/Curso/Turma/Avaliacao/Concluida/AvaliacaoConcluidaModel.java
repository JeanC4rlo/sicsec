/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Concluida;

import br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta.AvaliacaoModel;
import br.cefetmg.sicsec.Model.Usuario.Aluno.AlunoModel;
import java.util.List;

/**
 *
 * @author davig
 */


public class AvaliacaoConcluidaModel extends AvaliacaoModel {

    private AlunoModel aluno;
    private int notaAluno;
    private List<IQuestaoConcluida> respostas;    

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public int getNotaAluno() {
        return notaAluno;
    }

    public void setNotaAluno(int notaAluno) {
        this.notaAluno = notaAluno;
    }

    public List<IQuestaoConcluida> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<IQuestaoConcluida> respostas) {
        this.respostas = respostas;
    }
   
}