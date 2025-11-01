/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Administrador;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import jakarta.persistence.*;

/**
 *
 * @author davig
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Administrador extends Usuario {}