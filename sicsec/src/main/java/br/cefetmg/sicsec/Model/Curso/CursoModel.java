/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso;

import br.cefetmg.sicsec.Model.Curso.Turma.TurmaModel;
import java.util.List;

/**
 *
 * @author davig
 */
public class CursoModel {
    
    private String codigo;
    private String nome;
    private List<DisciplinaModel> matrizCurricular;
    private List<TurmaModel> historicoTurmas;

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

    public List<DisciplinaModel> getMatrizCurricular() {
        return matrizCurricular;
    }

    public void setMatrizCurricular(List<DisciplinaModel> matrizCurricular) {
        this.matrizCurricular = matrizCurricular;
    }

    public List<TurmaModel> getHistoricoTurmas() {
        return historicoTurmas;
    }

    public void setHistoricoTurmas(List<TurmaModel> historicoTurmas) {
        this.historicoTurmas = historicoTurmas;
    }
    
    
    
}
