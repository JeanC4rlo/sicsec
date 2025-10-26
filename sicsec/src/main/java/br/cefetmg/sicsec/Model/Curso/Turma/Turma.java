/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Curso.Turma;

import br.cefetmg.sicsec.Model.Curso.MaterialDidatico;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Curso.Turma.presenca.ListaPresenca;
import br.cefetmg.sicsec.Model.Curso.Turma.Avaliacao.Proposta.Avaliacao;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Util.Enum.TipoTurma;
import java.util.Date;
import java.util.List;

/**
 *
 * @author davig
 */
public class Turma {
    
    private TipoTurma tipo;
    private Disciplina disciplina;
    private List<Aluno> discentes;
    private List<Professor> doscentes;
    private List<Avaliacao> avaliacoes;
    private List<MaterialDidatico> materialDidatico;
    private List<Noticia> noticias;
    private List<ListaPresenca> frequencia;

    public TipoTurma getTipo() {
        return tipo;
    }

    public void setTipo(TipoTurma tipo) {
        this.tipo = tipo;
    }
    
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Aluno> getDiscentes() {
        return discentes;
    }

    public void setDiscentes(List<Aluno> discentes) {
        this.discentes = discentes;
    }

    public List<Professor> getDoscentes() {
        return doscentes;
    }

    public void setDoscentes(List<Professor> doscentes) {
        this.doscentes = doscentes;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<MaterialDidatico> getMaterialDidatico() {
        return materialDidatico;
    }

    public void setMaterialDidatico(List<MaterialDidatico> materialDidatico) {
        this.materialDidatico = materialDidatico;
    }

    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    public List<ListaPresenca> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(List<ListaPresenca> frequencia) {
        this.frequencia = frequencia;
    }
    
}
