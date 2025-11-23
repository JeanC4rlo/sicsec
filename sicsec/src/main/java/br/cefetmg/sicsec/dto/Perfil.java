package br.cefetmg.sicsec.dto;

import br.cefetmg.sicsec.Model.Usuario.Usuario;

public class Perfil {
    private Usuario usuario;
    private Boolean logado;
    
    public Perfil(Usuario usuario, Boolean logado) {
        this.usuario = usuario;
        this.logado = logado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Boolean getLogado() {
        return logado;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLogado(Boolean logado) {
        this.logado = logado;
    }
}
