/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

import br.cefetmg.sicsec.Model.Curso.MaterialDidatico;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Curso.Turma.presenca.ListaPresenca;
import br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta.Avaliacao;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Util.Enum.TipoTurma;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.Date;
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
    
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;
    
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
    
}
