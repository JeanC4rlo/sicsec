package br.cefetmg.sicsec.Model.Usuario.Aluno;

import br.cefetmg.sicsec.Model.Util.Enum.Situacao;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Boletim {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @OneToMany(mappedBy = "boletim", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComponenteCurricular> componentes;

    @Enumerated(EnumType.STRING)
    private Situacao situacaoDoAno;

    private int anoLetivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<ComponenteCurricular> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteCurricular> componentes) {
        this.componentes = componentes;
        if (componentes != null) {
            componentes.forEach(c -> c.setBoletim(this));
        }
    }

    public Situacao getSituacaoDoAno() {
        return situacaoDoAno;
    }

    public void setSituacaoDoAno(Situacao situacaoDoAno) {
        this.situacaoDoAno = situacaoDoAno;
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }
}