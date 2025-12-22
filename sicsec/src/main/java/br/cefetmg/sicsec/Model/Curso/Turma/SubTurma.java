/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Util.Enum.TipoTurma;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author davig
 */
@Entity
public class SubTurma extends Turma {

    @ManyToOne
    @JoinColumn(name = "super_turma_id", nullable = false)
    @JsonBackReference
    private SuperTurma superTurma;

    
    public SubTurma() {
        
    }
    
    public SubTurma(String nome, List<Aluno> discentes, List<Professor> doscentes, SuperTurma superTurma) {

        super(nome, superTurma.getAnoLetivo(), superTurma.isAtivo(), superTurma.getDisciplina(), superTurma.getCurso());
        
        this.setTipo(TipoTurma.SUBTURMA);
        this.setSuperTurma(superTurma);
        this.setDiscentes(discentes);
        this.setDoscentes(doscentes);

    }

    public SubTurma(String nome, SuperTurma turma) {

        super(nome, turma.getAnoLetivo(), turma.isAtivo(), turma.getDisciplina(), turma.getCurso());

        this.setTipo(TipoTurma.SUBTURMA);
        this.setSuperTurma(turma);
    }

    @Override
    public List<Aluno> getDiscentes() {
        return super.getDiscentes();
    }

    @Override
    public void setDiscentes(List<Aluno> discentes) {

        for (Aluno aluno : discentes) {
            if (!this.getSuperTurma().getDiscentes().contains(aluno)) {
                throw new IllegalArgumentException("O Aluno " + aluno.getMatricula().getNome() + " não está associado à Turma.");
            }
        }

        super.setDiscentes(discentes);
    }

    @Override
    public List<Professor> getDoscentes() {
        return super.getDoscentes();
    }

    @Override
    public void setDoscentes(List<Professor> doscentes) {

        for (Professor professor : doscentes) {
            if (!this.getSuperTurma().getDoscentes().contains(professor)) {
                throw new IllegalArgumentException("O Professor " + professor.getMatricula().getNome() + " não está associado à Turma.");
            }
        }

        super.setDoscentes(doscentes);
    }
    
    public SuperTurma getSuperTurma() {
        return superTurma;
    }

    public void setSuperTurma(SuperTurma superTurma) {
        this.superTurma = superTurma;
    }

}