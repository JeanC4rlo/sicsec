/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Aluno;

import br.cefetmg.sicsec.Model.Curso.DisciplinaModel;
import br.cefetmg.sicsec.Model.Util.Enum.Aprovacao;
import java.util.List;

/**
 *
 * @author davig
 */
public class BoletimModel {
    
    private List<ComponenteCurricular> componentes;

    public List<ComponenteCurricular> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteCurricular> componentes) {
        this.componentes = componentes;
    }
    
}

class ComponenteCurricular {
    
    private DisciplinaModel disciplina;
    private int[] notas;
    private int faltas;
    private int notaFinal;
    private Aprovacao situacao;

    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
    }

    public int[] getNotas() {
        return notas;
    }

    public void setNotas(int[] notas) {
        this.notas = notas;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(int notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Aprovacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Aprovacao situacao) {
        this.situacao = situacao;
    }
    
    
    
}