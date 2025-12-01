/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Professor;

import br.cefetmg.sicsec.Model.Curso.Departamento;
import br.cefetmg.sicsec.Model.Curso.Turma.*;
import br.cefetmg.sicsec.Model.Usuario.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author davig
 */

@Entity
public class Professor extends Usuario {
    
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "doscentes")
    private List<Turma> turmas;
    
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bolsistas")
    private List<Bolsa> bolsas;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "autor")
    private List<ProducaoAcademica> producoesAcademicas;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "professor")
    private List<Lecionamento> historicoLecionamento;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "professor")
    private List<Afastamento> historicoAfastamento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id", nullable = true)
    private Departamento departamento;

    private String formacao;

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

    public List<ProducaoAcademica> getProducoesAcademicas() {
        return producoesAcademicas;
    }

    public void setProducoesAcademicas(List<ProducaoAcademica> producoesAcademicas) {
        this.producoesAcademicas = producoesAcademicas;
    }
    
    public List<Lecionamento> getHistoricoLecionamento() {
        return historicoLecionamento;
    }

    public void setHistoricoLecionamento(List<Lecionamento> historicoLecionamento) {
        this.historicoLecionamento = historicoLecionamento;
    }

    public List<Afastamento> getHistoricoAfastamento() {
        return historicoAfastamento;
    }

    public void setHistoricoAfastamento(List<Afastamento> historicoAfastamento) {
        this.historicoAfastamento = historicoAfastamento;
    }
    
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

}
