package br.cefetmg.sicsec.Model.Usuario.Professor;

import br.cefetmg.sicsec.Model.Curso.Turma.*;
import br.cefetmg.sicsec.Model.Usuario.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Professor extends Usuario {
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "doscentes")
    private List<Turma> turmas;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bolsistas")
    private List<Bolsa> bolsas;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "autor")
    private List<ProducaoAcademica> producoesAcademicas;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "professor")
    private List<Lecionamento> historicoLecionamento;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "professor")
    private List<Afastamento> historicoAfastamento;

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
}
