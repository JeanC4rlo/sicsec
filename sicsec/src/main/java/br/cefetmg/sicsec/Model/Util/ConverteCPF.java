/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 *
 * @author davig
 */
@Converter(autoApply = true)
public class ConverteCPF implements AttributeConverter<CPF, Long> {
    
    @Override
    public Long convertToDatabaseColumn(CPF cpf) {
        if (cpf == null) {
            return null;
        }
        // Constrói um único número com base nos campos do CPF
        // Exemplo: digitosUnicos (9 primeiros) + 3 últimos dígitos
        long numero = cpf.getDigitosUnicos() * 1000L
                + cpf.getRegiaoFiscal() * 100L
                + cpf.getDigitoVerificador1() * 10L
                + cpf.getDigitoVerificador2();
        return numero;
    }

    @Override
    public CPF convertToEntityAttribute(Long dbData) {
        if (dbData == null) {
            return null;
        }

        CPF cpf = new CPF();
        cpf.setDigitoVerificador2((byte) (dbData % 10));
        dbData /= 10;
        cpf.setDigitoVerificador1((byte) (dbData % 10));
        dbData /= 10;
        cpf.setRegiaoFiscal((byte) (dbData % 10));
        dbData /= 10;
        cpf.setDigitosUnicos(dbData.intValue());
        return cpf;
    }
    
}
