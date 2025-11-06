package br.cefetmg.sicsec.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusAtividade statusAtividade;

    private Integer numQuestao;
    private Integer numAlternativa;
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

    public StatusAtividade getStatusAtividade() {
        return statusAtividade;
    }

    public void setStatusAtividade(StatusAtividade statusAtividade) {
        this.statusAtividade = statusAtividade;
    }

    public Integer getNumQuestao() {
        return numQuestao;
    }

    public void setNumQuestao(Integer numQuestao) {
        this.numQuestao = numQuestao;
    }

    public Integer getNumAlternativa() {
        return numAlternativa;
    }

    public void setNumAlternativa(Integer numAlternativa) {
        this.numAlternativa = numAlternativa;
    }

    public Boolean getCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }
}
