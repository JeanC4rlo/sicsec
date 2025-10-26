/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso;

import br.cefetmg.sicsec.Model.Util.Enum.DiaSemana;
import br.cefetmg.sicsec.Model.Util.GradeHorarios;
import br.cefetmg.sicsec.Model.Util.Horario;

/**
 *
 * @author davig
 */
public class AulaModel {
    
    private DiaSemana dia;
    private Horario horario;
    private String sala;

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
    
}