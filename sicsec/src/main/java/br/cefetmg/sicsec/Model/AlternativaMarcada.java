package br.cefetmg.sicsec.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class AlternativaMarcada {

    private Integer numQuestao;
    private Integer alternativa;
    private Boolean correta;

    public AlternativaMarcada() {}

    public AlternativaMarcada(Integer numQuestao, Integer alternativa, Boolean correta) {
        this.numQuestao = numQuestao;
        this.alternativa = alternativa;
        this.correta = correta;
    }

    public Integer getNumQuestao() {
        return numQuestao;
    }

    public void setNumQuestao(Integer numQuestao) {
        this.numQuestao = numQuestao;
    }

    public Integer getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(Integer alternativa) {
        this.alternativa = alternativa;
    }

    public Boolean isCorreta() {
        return correta;
    }

    public void setCorreta(Boolean correta) {
        this.correta = correta;
    }
}
