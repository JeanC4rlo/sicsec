/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Aluno;

import br.cefetmg.sicsec.Model.Turma.TurmaModel;
import br.cefetmg.sicsec.Model.Usuario.BolsaModel;
import br.cefetmg.sicsec.Model.Usuario.MatriculaModel;
import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import java.util.List;

/**
 *
 * @author davig
 */
public class AlunoModel extends UsuarioModel {
    
    private List<NescessidadeEspecialModel> nescessidadesEspeciais;
    private List<MatriculaModel> historicoMatricula;
    private BoletimModel boletim;
    private List<BoletimModel> historicoAcademico;
    private List<TurmaModel> turmas;
    private List<BolsaModel> bolsas;

    public List<NescessidadeEspecialModel> getNescessidadesEspeciais() {
        return nescessidadesEspeciais;
    }

    public void setNescessidadesEspeciais(List<NescessidadeEspecialModel> nescessidadesEspeciais) {
        this.nescessidadesEspeciais = nescessidadesEspeciais;
    }

    public List<MatriculaModel> getHistoricoMatricula() {
        return historicoMatricula;
    }

    public void setHistoricoMatricula(List<MatriculaModel> historicoMatricula) {
        this.historicoMatricula = historicoMatricula;
    }

    public BoletimModel getBoletim() {
        return boletim;
    }

    public void setBoletim(BoletimModel boletim) {
        this.boletim = boletim;
    }

    public List<BoletimModel> getHistoricoAcademico() {
        return historicoAcademico;
    }

    public void setHistoricoAcademico(List<BoletimModel> historicoAcademico) {
        this.historicoAcademico = historicoAcademico;
    }

    public List<TurmaModel> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<TurmaModel> turmas) {
        this.turmas = turmas;
    }

    public List<BolsaModel> getBolsas() {
        return bolsas;
    }

    public void setBolsas(List<BolsaModel> bolsas) {
        this.bolsas = bolsas;
    }
    
}
