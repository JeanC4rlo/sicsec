/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Turma;

import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;

/**
 *
 * @author davig
 */
class NoticiaModel {
    
    private String manchete;
    private String corpo;
    private UsuarioModel autor;

    public String getManchete() {
        return manchete;
    }

    public void setManchete(String manchete) {
        this.manchete = manchete;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public UsuarioModel getAutor() {
        return autor;
    }

    public void setAutor(UsuarioModel autor) {
        this.autor = autor;
    }
    
}
