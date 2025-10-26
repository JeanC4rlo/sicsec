/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Aluno;

import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author davig
 */
@Entity
public class Aluno extends Usuario {
    
    private int boletim;
    private String turmas;
    
    @JsonManagedReference
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bolsistas")
    private List<Bolsa> bolsas;
    
    /*
    private List<Boletim> historicoAcademico;
    private List<Matricula> historicoMatricula;
    private List<NescessidadeEspecial> nescessidadesEspeciais;
    */

    public int getBoletim() {
        return boletim;
    }

    public void setBoletim(int boletim) {
        this.boletim = boletim;
    }

    public String getTurmas() {
        return turmas;
    }

    public void setTurmas(String turmas) {
        this.turmas = turmas;
    }

    public List<Bolsa> getBolsas() {
        return bolsas;
    }

    public void setBolsas(List<Bolsa> bolsas) {
        this.bolsas = bolsas;
    }

    
}
