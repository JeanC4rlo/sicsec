/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso;

import br.cefetmg.sicsec.Model.Usuario.Administrador.ChefeDepartamento;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author davig
 */
@Entity
public class Departamento {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordenador_id")
    private ChefeDepartamento chefe;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy="departamento")
    private List<Curso> cursos;
    
    @JsonManagedReference(value = "disciplinaDepartamento")
    @OneToMany(fetch = FetchType.LAZY, mappedBy="departamento")
    private List<Disciplina> disciplinas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ChefeDepartamento getChefe() {
        return chefe;
    }

    public void setChefe(ChefeDepartamento chefe) {
        this.chefe = chefe;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
    
}
