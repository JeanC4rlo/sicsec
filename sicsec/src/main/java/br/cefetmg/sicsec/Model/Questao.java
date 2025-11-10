package br.cefetmg.sicsec.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Embeddable
public class Questao {
    private String enunciado;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "questao_id")
    private List<Alternativa> alternativas;
    
    private Integer idxCorreta;

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    public Integer getIdxCorreta() {
        return idxCorreta;
    }

    public void setIdxCorreta(Integer correta) {
        this.idxCorreta = correta;
    }
}
