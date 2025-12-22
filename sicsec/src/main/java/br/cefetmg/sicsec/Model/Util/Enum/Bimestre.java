/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Util.Enum;

/**
 *
 * @author davig
 */
public enum Bimestre {
    PRIMEIRO,
    SEGUNDO,
    TERCEIRO,
    QUARTO;

    public static Bimestre getByMonth(int mes) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês inválido: " + mes);
        }

        if (mes <= 3)
            return PRIMEIRO;
        if (mes <= 6)
            return SEGUNDO;
        if (mes <= 9)
            return TERCEIRO;
        return QUARTO;
    }
}
