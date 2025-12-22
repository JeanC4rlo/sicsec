package br.cefetmg.sicsec.Model.Usuario;

import java.util.Date;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Bolsa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String tipo;
    private double valorMensal;
    private int vagas;
    
    @Temporal(TemporalType.DATE)
    private Date inicio;
    
    @Temporal(TemporalType.DATE)
    private Date fim;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "bolsista",
        joinColumns = @JoinColumn(name = "id_bolsa"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> bolsistas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(double valorMensal) {
        this.valorMensal = valorMensal;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public List<Usuario> getBolsistas() {
        return bolsistas;
    }

    public void setBolsistas(List<Usuario> bolsistas) {
        this.bolsistas = bolsistas;
    }

    
    
}
