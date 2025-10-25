/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario.Professor;

import br.cefetmg.sicsec.Model.Usuario.ProducaoAcademicaModel;
import br.cefetmg.sicsec.Model.Enum.Afastamento;
import br.cefetmg.sicsec.Model.Turma.TurmaModel;
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
    private List<Afastamento> historicoAfastamento;
    private List<BolsaModel> bolsas;
    private List<ProducaoAcademicaModel> producoesAcademicas;
    
}
