/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

/**
 *
 * @author davig
 */
@Embeddable
public class CPF {
    
    @Column(name = "cpf")
    private Long cpf;
    
    @Transient
    private String cpfFormatado;
    
    public Long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getCpfFormatado() {
        return cpfFormatado;
    }

    public void setCpfFormatado(String cpfFormatado) {
        this.cpfFormatado = cpfFormatado;
    }
 
}