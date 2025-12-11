package br.cefetmg.sicsec.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
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
    @JsonIgnore
    @JoinColumn(name = "atividade_id", nullable = false)
    Atividade atividade;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "tentativa_id", nullable = true)
    Tentativa tentativa;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "resposta_id", nullable = false)
    Resposta resposta;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

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

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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
