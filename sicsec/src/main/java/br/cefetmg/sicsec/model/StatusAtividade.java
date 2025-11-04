package br.cefetmg.sicsec.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class StatusAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    /*@ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;*/

    private LocalDateTime horarioInicio;
    private Long tempoRestante;
    private Integer numTentativa;
    private Boolean enviada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    /*public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }*/

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }
    
    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }
    
    public Long getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante(Long tempoRestante) {
        this.tempoRestante = tempoRestante;
    }

    public Integer getNumTentativa() {
        return numTentativa;
    }

    public void setNumTentativa(Integer tentativas) {
        this.numTentativa = tentativas;
    }

    public Boolean getEnviada() {
        return enviada;
    }

    public void setEnviada(Boolean enviada) {
        this.enviada = enviada;
    }
}
