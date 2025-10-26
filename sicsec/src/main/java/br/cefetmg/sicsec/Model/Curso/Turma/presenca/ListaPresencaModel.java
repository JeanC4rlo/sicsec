/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.presenca;

import br.cefetmg.sicsec.Model.Usuario.Aluno.AlunoModel;
import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import java.util.List;

/**
 *
 * @author davig
 */
public class ListaPresencaModel {
    
    private UsuarioModel autor;
    private List<PresencaModel> presencas;

    public UsuarioModel getAutor() {
        return autor;
    }

    public void setAutor(UsuarioModel autor) {
        this.autor = autor;
    }

    public List<PresencaModel> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<PresencaModel> presencas) {
        this.presencas = presencas;
    }

}