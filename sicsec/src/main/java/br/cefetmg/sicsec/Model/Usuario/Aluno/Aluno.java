/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Aluno;

import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.*;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author davig
 */
@Entity
public class Aluno extends Usuario {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "discentes")
    private List<Turma> turmas;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bolsistas")
    private List<Bolsa> bolsas;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "aluno")
    @OrderBy("anoLetivo DESC")
    private List<Boletim> anosEscolares;

    /*
     * @JsonIgnore
     * 
     * @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy =
     * "usuario")
     * private List<Matricula> historicoMatricula;
     */

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "aluno")
    private List<NescessidadeEspecial> nescessidadesEspeciais;

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public List<Bolsa> getBolsas() {
        return bolsas;
    }

    public void setBolsas(List<Bolsa> bolsas) {
        this.bolsas = bolsas;
    }

    public List<Boletim> getAnosEscolares() {
        return anosEscolares;
    }

    public void setAnosEscolares(List<Boletim> anosEscolares) {
        this.anosEscolares = anosEscolares;
    }

    public List<NescessidadeEspecial> getNescessidadesEspeciais() {
        return nescessidadesEspeciais;
    }

    public void setNescessidadesEspeciais(List<NescessidadeEspecial> nescessidadesEspeciais) {
        this.nescessidadesEspeciais = nescessidadesEspeciais;
    }

    public Boletim getBoletimAtual() {
        if (anosEscolares == null || anosEscolares.isEmpty()) {
            return null;
        }
        return anosEscolares.get(0);
    }
}