package br.cefetmg.sicsec.Model;

//import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class AtividadeEnviada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    /*@ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;*/

    private Double nota;
    private LocalDateTime horarioEnvio;
    private String resposta;
    private String nomeArquivoEnviado;
    private Integer numTentativa;

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

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public LocalDateTime getHorarioEnvio() {
        return horarioEnvio;
    }

    public void setHorarioEnvio(LocalDateTime horarioEnvio) {
        this.horarioEnvio = horarioEnvio;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getNomeArquivoEnviado() {
        return nomeArquivoEnviado;
    }

    public void setNomeArquivoEnviado(String nomeArquivoEnviado) {
        this.nomeArquivoEnviado = nomeArquivoEnviado;
    }

    public Integer getNumTentativa() {
        return numTentativa;
    }

    public void setNumTentativa(Integer numTentativa) {
        this.numTentativa = numTentativa;
    }
}
