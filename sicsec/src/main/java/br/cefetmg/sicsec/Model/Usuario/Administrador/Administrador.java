/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Administrador;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.CargoAdministrador;
import jakarta.persistence.*;

/**
 *
 * @author davig
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Administrador extends Usuario {

    @Enumerated(EnumType.STRING)
    private CargoAdministrador cargoAdministrador;

    public CargoAdministrador getCargoAdministrador() {
        return cargoAdministrador;
    }

    public void setCargoAdministrador(CargoAdministrador cargoAdministrador) {
        this.cargoAdministrador = cargoAdministrador;
    }
    
}