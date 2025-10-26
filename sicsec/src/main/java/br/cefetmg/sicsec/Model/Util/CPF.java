/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Util;

/**
 *
 * @author davig
 */
public class CPF {
    
    private int digitosUnicos;       // digitos em x: xxx.xxx.xx0-00
    private byte regiaoFiscal;       // digito em x:  000.000.00x-00
    private byte digitoVerificador1; // digito em x:  000.000.000-x0
    private byte digitoVerificador2; // digito em x:  000.000.000-0x

    public int getDigitosUnicos() {
        return digitosUnicos;
    }

    public void setDigitosUnicos(int digitosUnicos) {
        this.digitosUnicos = digitosUnicos;
    }

    public byte getRegiaoFiscal() {
        return regiaoFiscal;
    }

    public void setRegiaoFiscal(byte regiaoFiscal) {
        this.regiaoFiscal = regiaoFiscal;
    }

    public byte getDigitoVerificador1() {
        return digitoVerificador1;
    }

    public void setDigitoVerificador1(byte digitoVerificador1) {
        this.digitoVerificador1 = digitoVerificador1;
    }

    public byte getDigitoVerificador2() {
        return digitoVerificador2;
    }

    public void setDigitoVerificador2(byte digitoVerificador2) {
        this.digitoVerificador2 = digitoVerificador2;
    }
    
}