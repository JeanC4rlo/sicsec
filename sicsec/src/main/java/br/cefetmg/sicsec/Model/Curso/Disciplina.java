/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso;

import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Util.Enum.Area;
import java.util.List;

/**
 *
 * @author davig
 */

//Baseado nas linhas da seguinte tabela: https://www.decom.cefetmg.br/wp-content/uploads/sites/34/2017/03/matriz_curricular-Informatica.pdf
public class Disciplina {
    
    private Curso curso;
    private Area area;
    private String nome;
    private int[] cargaHoraria; //Número de horários da aula por semana.
    private int cargaHorariaTotal; //Carga horária total ao longo do ano.
    private double horasAulaTotal; //Carga Horária * 50 / 60

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int[] getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int[] cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getCargaHorariaTotal() {
        return cargaHorariaTotal;
    }

    public void setCargaHorariaTotal(int cargaHorariaTotal) {
        this.cargaHorariaTotal = cargaHorariaTotal;
    }

    public double getHorasAulaTotal() {
        return horasAulaTotal;
    }

    public void setHorasAulaTotal(double horasAulaTotal) {
        this.horasAulaTotal = horasAulaTotal;
    }
    
}
