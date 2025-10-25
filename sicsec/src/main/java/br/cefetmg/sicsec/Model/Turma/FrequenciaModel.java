/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Turma;

import br.cefetmg.sicsec.Model.Usuario.Aluno.AlunoModel;
import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import java.util.List;

/**
 *
 * @author davig
 */
class FrequenciaModel {
    
    private UsuarioModel autor;
    private List<AlunoModel> discentes;
    private List<Boolean> presente;

    public UsuarioModel getAutor() {
        return autor;
    }

    public void setAutor(UsuarioModel autor) {
        this.autor = autor;
    }

    public List<AlunoModel> getDiscentes() {
        return discentes;
    }

    public void setDiscentes(List<AlunoModel> discentes) {
        this.discentes = discentes;
    }

    public List<Boolean> getPresente() {
        return presente;
    }

    public void setPresente(List<Boolean> presente) {
        this.presente = presente;
    }
    
}
