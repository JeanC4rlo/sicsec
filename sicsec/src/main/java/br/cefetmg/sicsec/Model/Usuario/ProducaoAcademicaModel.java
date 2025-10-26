/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario;

import java.time.LocalDate;

/**
 *
 * @author davig
 */
public class ProducaoAcademicaModel {
    
    private String titulo;
    private String tipo;
    private LocalDate publicacao;
    private UsuarioModel autor;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(LocalDate publicacao) {
        this.publicacao = publicacao;
    }

    public UsuarioModel getAutor() {
        return autor;
    }

    public void setAutor(UsuarioModel autor) {
        this.autor = autor;
    }
    
    
    
}
