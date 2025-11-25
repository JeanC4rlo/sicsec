package br.cefetmg.sicsec.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Desempenho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    Atividade atividade;

    @OneToOne
    @JoinColumn(name = "tentativa_id", nullable = true)
    Tentativa tentativa;

    @OneToOne
    @JoinColumn(name = "resposta_id", nullable = false)
    Resposta resposta;

    private Double nota;
    private Boolean corrigido;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public Tentativa getTentativa() {
        return tentativa;
    }

    public void setTentativa(Tentativa tentativa) {
        this.tentativa = tentativa;
    }

    public Resposta getResposta() {
        return resposta;
    }

    public void setResposta(Resposta resposta) {
        this.resposta = resposta;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Boolean getCorrigido() {
        return corrigido;
    }

    public void setCorrigido(Boolean corrigido) {
        this.corrigido = corrigido;
    }
}
