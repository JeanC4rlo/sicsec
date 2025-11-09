package br.cefetmg.sicsec.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Tentativa statusAtividade;

    private Integer numQuestao;
    private Integer alternativaMarcada;
    private Boolean correta;

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

    public Tentativa getStatusAtividade() {
        return statusAtividade;
    }

    public void setStatusAtividade(Tentativa statusAtividade) {
        this.statusAtividade = statusAtividade;
    }

    public Integer getNumQuestao() {
        return numQuestao;
    }

    public void setNumQuestao(Integer numQuestao) {
        this.numQuestao = numQuestao;
    }

    public Integer getAlternativaMarcada() {
        return alternativaMarcada;
    }

    public void setAlternativaMarcada(Integer numAlternativa) {
        this.alternativaMarcada = numAlternativa;
    }

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }
}
