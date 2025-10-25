/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Enum;

/**
 *
 * @author davig
 */
public enum Cargo {
    
    ADMINISTRADOR, ALUNO, PROFESSOR, PESQUISADOR, BIBLIOTECARIO;
    
    public static Cargo ofName(String name) {
        return valueOf(name.toUpperCase());
    }
    
}
