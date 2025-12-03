package br.cefetmg.sicsec.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
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
public class Resposta implements FileOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @OneToOne
    @JoinColumn(name = "tentativa_id", nullable = true)
    private Tentativa tentativa;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ElementCollection
    private List<AlternativaMarcada> alternativasMarcadas;

    @Lob
    private String textoRedacao;
    private Long arquivoId;

    @Override
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

    public void setTentativa(Tentativa tentativa) {
        this.tentativa = tentativa;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
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

    public Long getArquivoId() {
        return arquivoId;
    }

    public void setArquivoId(Long arquivoId) {
        this.arquivoId = arquivoId;
    }

    public FileOwnerTypes getTipoDonoArquivo() {
        return FileOwnerTypes.RESPOSTA;
    }
}
