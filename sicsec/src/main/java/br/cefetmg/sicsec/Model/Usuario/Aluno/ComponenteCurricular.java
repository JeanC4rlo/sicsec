package br.cefetmg.sicsec.Model.Usuario.Aluno;

import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Util.Enum.Aprovacao;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class ComponenteCurricular {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "boletim_id", nullable = false)
    private Boletim boletim;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;
    
    @OneToMany(mappedBy = "componente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas;
    
    private int notaFinal;
    
    private int faltas;
    
    @Enumerated(EnumType.STRING)
    private Aprovacao situacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boletim getBoletim() {
        return boletim;
    }

    public void setBoletim(Boletim boletim) {
        this.boletim = boletim;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
        if (notas != null) {
            notas.forEach(n -> n.setComponente(this));
        }
    }

    public int getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(int notaFinal) {
        this.notaFinal = notaFinal;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public Aprovacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Aprovacao situacao) {
        this.situacao = situacao;
    }
}
