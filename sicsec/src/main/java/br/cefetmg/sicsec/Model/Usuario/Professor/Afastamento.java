/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Professor;

import java.util.Date;

/**
 *
 * @author davig
 */
public class Afastamento {
 
    private Date dataAfastamento;
    private Date dataRetorno;
    private String causa;

    public Date getDataAfastamento() {
        return dataAfastamento;
    }

    public void setDataAfastamento(Date dataAfastamento) {
        this.dataAfastamento = dataAfastamento;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    
    
}
