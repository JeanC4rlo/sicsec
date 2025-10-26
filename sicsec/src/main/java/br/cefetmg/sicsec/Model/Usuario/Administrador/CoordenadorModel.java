/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Administrador;

import br.cefetmg.sicsec.Model.Curso.CursoModel;

/**
 *
 * @author davig
 */
public class CoordenadorModel extends AdministradorModel {
    
    private CursoModel curso;

    public CursoModel getCurso() {
        return curso;
    }

    public void setCurso(CursoModel curso) {
        this.curso = curso;
    }
    
}
