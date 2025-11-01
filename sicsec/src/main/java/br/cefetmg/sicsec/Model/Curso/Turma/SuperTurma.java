/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
