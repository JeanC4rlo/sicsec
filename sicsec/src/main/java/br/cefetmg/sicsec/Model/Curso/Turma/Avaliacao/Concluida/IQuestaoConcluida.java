/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Concluida;
import br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta.IQuestao;

/**
 *
 * @author davig
 */

public interface IQuestaoConcluida extends IQuestao{
    
    public double getNotaObtida();

    public void setNotaObtida(double notaObtida);
    
    public String getComentario();
    
    public void setComentario(String comentario);
            
}
