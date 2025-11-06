/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Administrador;

import br.cefetmg.sicsec.Model.Curso.Departamento;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

/**
 *
 * @author davig
 */

@Entity
public class ChefeDepartamento extends Administrador {
    
    @OneToOne(mappedBy = "chefe")
    private Departamento departamento;

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
}