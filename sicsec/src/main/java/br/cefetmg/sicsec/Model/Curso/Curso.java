/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso;

import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import java.util.List;

/**
 *
 * @author davig
 */
public class Curso {
    
    private String codigo;
    private String nome;
    private List<Disciplina> matrizCurricular;
    private List<Turma> historicoTurmas;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Disciplina> getMatrizCurricular() {
        return matrizCurricular;
    }

    public void setMatrizCurricular(List<Disciplina> matrizCurricular) {
        this.matrizCurricular = matrizCurricular;
    }

    public List<Turma> getHistoricoTurmas() {
        return historicoTurmas;
    }

    public void setHistoricoTurmas(List<Turma> historicoTurmas) {
        this.historicoTurmas = historicoTurmas;
    }
    
    
    
}
