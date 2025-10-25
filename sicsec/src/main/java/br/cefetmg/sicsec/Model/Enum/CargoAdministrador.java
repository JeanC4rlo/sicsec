/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Enum;

/**
 *
 * @author davig
 */
public enum CargoAdministrador {
    
    CHEFE_DE_DEPARTAMENTO, COORDENADOR;
    
    public static CargoAdministrador ofName(String name) {
        return valueOf(name.toUpperCase());
    }
    
}
