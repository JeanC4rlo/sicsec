/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Util;

import br.cefetmg.sicsec.Model.Util.Enum.Turno;
import jakarta.persistence.*;
import java.time.LocalTime;

/**
 *
 * @author davig
 */
@Embeddable
public class Horario {
    
    private LocalTime inicio;
    private LocalTime fim;
    
    @Enumerated(EnumType.STRING)
    private Turno turno;
    
    @Transient
    int subHorario;

    public Horario() {}
    
    public Horario(LocalTime inicio, LocalTime fim, Turno turno, int subHorario) {
        this.inicio = inicio;
        this.fim = fim;
        this.turno = turno;
        this.subHorario = subHorario;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public int getSubHorario() {
        return subHorario;
    }

    public void setSubHorario(int subHorario) {
        this.subHorario = subHorario;
    }
    
}