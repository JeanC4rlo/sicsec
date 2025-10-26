/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Pesquisador;

import br.cefetmg.sicsec.Model.Usuario.Bolsa;
import br.cefetmg.sicsec.Model.Usuario.ProducaoAcademica;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;

/**
 *
 * @author davig
 */

@Entity
public class Pesquisador extends Usuario {
    
    @JsonManagedReference
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bolsistas")
    private List<Bolsa> bolsas;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "autor")
    private List<ProducaoAcademica> producoesAcademicas;

    
    public List<Bolsa> getBolsas() {
        return bolsas;
    }

    public void setBolsas(List<Bolsa> bolsas) {
        this.bolsas = bolsas;
    }

    public List<ProducaoAcademica> getProducoesAcademicas() {
        return producoesAcademicas;
    }

    public void setProducoesAcademicas(List<ProducaoAcademica> producoesAcademicas) {
        this.producoesAcademicas = producoesAcademicas;
    }
    
}
