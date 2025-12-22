package br.cefetmg.sicsec.Model.Usuario.Aluno;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Util.Enum.Bimestre;
import jakarta.persistence.*;

@Entity
public class Nota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "componente_id")
    private ComponenteCurricular componente;

    @OneToOne
    @JoinColumn(name = "desempenho_id")
    private Desempenho desempenho;
    
    @Enumerated(EnumType.STRING)
    private Bimestre bimestre;

    private Double valor;
    private String avaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComponenteCurricular getComponente() {
        return componente;
    }

    public void setComponente(ComponenteCurricular componente) {
        this.componente = componente;
    }

    public Bimestre getBimestre() {
        return bimestre;
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre;
    }

    public Desempenho getDesempenho() {
        return desempenho;
    }

    public void setDesempenho(Desempenho desempenho) {
        this.desempenho = desempenho;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    
}
