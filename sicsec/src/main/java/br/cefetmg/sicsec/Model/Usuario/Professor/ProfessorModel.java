/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Professor;

import br.cefetmg.sicsec.Model.Usuario.ProducaoAcademicaModel;
import br.cefetmg.sicsec.Model.Curso.Turma.TurmaModel;
import br.cefetmg.sicsec.Model.Usuario.BolsaModel;
import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import java.util.List;

/**
 *
 * @author davig
 */
public class ProfessorModel extends UsuarioModel {
    
    private List<TurmaModel> turmas;
    private List<LecionamentoModel> historicoLecionamento;
    private List<AfastamentoModel> historicoAfastamento;
    private List<BolsaModel> bolsas;
    private List<ProducaoAcademicaModel> producoesAcademicas;

    public List<TurmaModel> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<TurmaModel> turmas) {
        this.turmas = turmas;
    }

    public List<LecionamentoModel> getHistoricoLecionamento() {
        return historicoLecionamento;
    }

    public void setHistoricoLecionamento(List<LecionamentoModel> historicoLecionamento) {
        this.historicoLecionamento = historicoLecionamento;
    }

    public List<AfastamentoModel> getHistoricoAfastamento() {
        return historicoAfastamento;
    }

    public void setHistoricoAfastamento(List<AfastamentoModel> historicoAfastamento) {
        this.historicoAfastamento = historicoAfastamento;
    }

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
