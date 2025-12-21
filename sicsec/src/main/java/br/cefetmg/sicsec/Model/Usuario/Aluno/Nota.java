package br.cefetmg.sicsec.Model.Usuario.Aluno;

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
    
    @Enumerated(EnumType.STRING)
    private Bimestre bimestre;
    
    private int valor;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }
}
