/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.presenca;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import jakarta.persistence.*;
/**
 *
 * @author davig
 */

@Entity
public class Presenca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_id")
    private ListaPresenca lista;
    
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno discente;
    
    private boolean presente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListaPresenca getLista() {
        return lista;
    }

    public void setLista(ListaPresenca lista) {
        this.lista = lista;
    }

    public Aluno getDiscente() {
        return discente;
    }

    public void setDiscente(Aluno discente) {
        this.discente = discente;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
    
    
    
}