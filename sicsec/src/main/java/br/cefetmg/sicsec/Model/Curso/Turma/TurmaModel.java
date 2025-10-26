/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

import br.cefetmg.sicsec.Model.Curso.MaterialDidaticoModel;
import br.cefetmg.sicsec.Model.Curso.DisciplinaModel;
import br.cefetmg.sicsec.Model.Curso.Turma.presenca.ListaPresencaModel;
import br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta.AvaliacaoModel;
import br.cefetmg.sicsec.Model.Usuario.Aluno.AlunoModel;
import br.cefetmg.sicsec.Model.Usuario.Professor.ProfessorModel;
import br.cefetmg.sicsec.Model.Util.Enum.TipoTurma;
import java.util.Date;
import java.util.List;

/**
 *
 * @author davig
 */
public class TurmaModel {
    
    private TipoTurma tipo;
    private DisciplinaModel disciplina;
    private List<AlunoModel> discentes;
    private List<ProfessorModel> doscentes;
    private List<AvaliacaoModel> avaliacoes;
    private List<MaterialDidaticoModel> materialDidatico;
    private List<NoticiaModel> noticias;
    private List<ListaPresencaModel> frequencia;

    public TipoTurma getTipo() {
        return tipo;
    }

    public void setTipo(TipoTurma tipo) {
        this.tipo = tipo;
    }
    
    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
    }

    public List<AlunoModel> getDiscentes() {
        return discentes;
    }

    public void setDiscentes(List<AlunoModel> discentes) {
        this.discentes = discentes;
    }

    public List<ProfessorModel> getDoscentes() {
        return doscentes;
    }

    public void setDoscentes(List<ProfessorModel> doscentes) {
        this.doscentes = doscentes;
    }

    public List<AvaliacaoModel> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoModel> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<MaterialDidaticoModel> getMaterialDidatico() {
        return materialDidatico;
    }

    public void setMaterialDidatico(List<MaterialDidaticoModel> materialDidatico) {
        this.materialDidatico = materialDidatico;
    }

    public List<NoticiaModel> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<NoticiaModel> noticias) {
        this.noticias = noticias;
    }

    public List<ListaPresencaModel> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(List<ListaPresencaModel> frequencia) {
        this.frequencia = frequencia;
    }
    
}
