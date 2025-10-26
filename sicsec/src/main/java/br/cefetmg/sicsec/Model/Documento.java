/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author davig
 */
public class Documento {
 
    private String titulo;
    private String conteudo;
    private LocalDateTime dataCriacao;
    private List<Usuario> assinantes;
    private List<LocalDateTime> datasAssinaturas;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Usuario> getAssinantes() {
        return assinantes;
    }

    public void setAssinantes(List<Usuario> assinantes) {
        this.assinantes = assinantes;
    }

    public List<LocalDateTime> getDatasAssinaturas() {
        return datasAssinaturas;
    }

    public void setDatasAssinaturas(List<LocalDateTime> datasAssinaturas) {
        this.datasAssinaturas = datasAssinaturas;
    }
    
    
    
}