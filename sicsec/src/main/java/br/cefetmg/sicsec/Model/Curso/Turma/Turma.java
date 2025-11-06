/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

import br.cefetmg.sicsec.Model.Curso.*;
import br.cefetmg.sicsec.Model.Curso.Turma.presenca.ListaPresenca;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Util.Enum.TipoTurma;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davig
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Turma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TipoTurma tipo;
    
    private String nome;
    
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "turma_aluno",
        joinColumns = @JoinColumn(name = "turma_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> discentes;
    
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "turma_professor",
        joinColumns = @JoinColumn(name = "turma_id"),
        inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    private List<Professor> doscentes;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
    private List<Aula> aulas;
    
    /*
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
    private List<Avaliacao> avaliacoes;
    */

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
    private List<MaterialDidatico> materialDidatico;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
    private List<Noticia> noticias;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "turma")
    private List<ListaPresenca> frequencia;

    public Turma() {
        
    }
    
    public Turma(String nome, Disciplina disciplina, Curso curso, List<Aluno> discentes, List<Professor> doscentes) {
        this.setNome(nome);
        this.setDisciplina(disciplina);
        this.setCurso(curso);
        this.setDiscentes(discentes);
        this.setDoscentes(doscentes);
        this.setTipo(TipoTurma.TURMA_UNICA);
    }
    
    public Turma(String nome, Disciplina disciplina, Curso curso) {
        this.setNome(nome);
        this.setDisciplina(disciplina);
        this.setCurso(curso);
        this.setDiscentes(new ArrayList<Aluno>());
        this.setDoscentes(new ArrayList<Professor>());
        this.setTipo(TipoTurma.TURMA_UNICA);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTurma getTipo() {
        return tipo;
    }

    public void setTipo(TipoTurma tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Aluno> getDiscentes() {
        return discentes;
    }

    public void setDiscentes(List<Aluno> discentes) {
        this.discentes = discentes;
    }

    public List<Professor> getDoscentes() {
        return doscentes;
    }

    public void setDoscentes(List<Professor> doscentes) {
        this.doscentes = doscentes;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public List<MaterialDidatico> getMaterialDidatico() {
        return materialDidatico;
    }

    public void setMaterialDidatico(List<MaterialDidatico> materialDidatico) {
        this.materialDidatico = materialDidatico;
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    public List<ListaPresenca> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(List<ListaPresenca> frequencia) {
        this.frequencia = frequencia;
    }
    
}
