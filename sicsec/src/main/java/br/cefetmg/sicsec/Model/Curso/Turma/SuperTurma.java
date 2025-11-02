/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Util.Enum.TipoTurma;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davig
 */
@Entity
public class SuperTurma extends Turma {

    @OneToMany(mappedBy = "superTurma", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubTurma> subTurmas;

    public SuperTurma() {
        
    }
    
    public SuperTurma(String nome, Disciplina disciplina, Curso curso, List<Aluno> discentes, List<Professor> doscentes) {
        super(nome, disciplina, curso, discentes, doscentes);
        this.setTipo(TipoTurma.SUPERTURMA);
    }
    
    public SuperTurma(String nome, Disciplina disciplina, Curso curso) {
        super(nome, disciplina, curso);
        this.setTipo(TipoTurma.SUPERTURMA);
    }
    
    public List<SubTurma> getSubTurmas() {
        return subTurmas;
    }

    public void setSubTurmas(List<SubTurma> subTurmas) {
        
        if (subTurmas != null)
            for (SubTurma subt : subTurmas) 
                if (subt.getSuperTurma() != this)
                    subt.setSuperTurma(this);

        this.subTurmas = subTurmas;
    
    }
    
}
