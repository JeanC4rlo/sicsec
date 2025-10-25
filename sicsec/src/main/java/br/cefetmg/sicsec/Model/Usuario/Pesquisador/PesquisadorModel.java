/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Pesquisador;

import br.cefetmg.sicsec.Model.Usuario.BolsaModel;
import br.cefetmg.sicsec.Model.Usuario.ProducaoAcademicaModel;
import java.util.List;

/**
 *
 * @author davig
 */
public class PesquisadorModel {
    
    private List<BolsaModel> bolsas;
    private List<ProducaoAcademicaModel> producoesAcademicas;

    public List<BolsaModel> getBolsas() {
        return bolsas;
    }

    public void setBolsas(List<BolsaModel> bolsas) {
        this.bolsas = bolsas;
    }

    public List<ProducaoAcademicaModel> getProducoesAcademicas() {
        return producoesAcademicas;
    }

    public void setProducoesAcademicas(List<ProducaoAcademicaModel> producoesAcademicas) {
        this.producoesAcademicas = producoesAcademicas;
    }
    
}
