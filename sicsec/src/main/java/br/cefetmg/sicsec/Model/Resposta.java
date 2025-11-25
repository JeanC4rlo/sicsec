package br.cefetmg.sicsec.Model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @OneToOne
    @JoinColumn(name = "tentativa_id", nullable = true)
    private Tentativa tentativa;

    @ElementCollection
    private List<AlternativaMarcada> alternativasMarcadas;

    @Lob
    private String textoRedacao;
    private String nomeArquivo;

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

    public Tentativa getTentativa() {
        return tentativa;
    }

    public void setTentativa(Tentativa statusAtividade) {
        this.tentativa = statusAtividade;
    }

    public List<AlternativaMarcada> getAlternativasMarcadas() {
        return alternativasMarcadas;
    }

    public void setAlternativasMarcadas(List<AlternativaMarcada> alternativasMarcadas) {
        this.alternativasMarcadas = alternativasMarcadas;
    }

    public String getTextoRedacao() {
        return textoRedacao;
    }

    public void setTextoRedacao(String textoRedacao) {
        this.textoRedacao = textoRedacao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
