/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Turma;

import br.cefetmg.sicsec.Model.Usuario.Aluno.AlunoModel;
import br.cefetmg.sicsec.Model.Usuario.Professor.ProfessorModel;
import java.util.Date;
import java.util.List;

/**
 *
 * @author davig
 */
public class TurmaModel {
    
    private Long id;
    private List<AlunoModel> discentes;
    private List<ProfessorModel> doscentes;
    private String sala;
    private Date horario;
    private List<AvaliacaoModel> avaliacoes;
    private DisciplinaModel disciplina;
    private List<MaterialDidaticoModel> materialDidatico;
    private List<NoticiaModel> noticias;
    private List<FrequenciaModel> frequencia;
    private CronogramaModel cronograma;
    private List<SubTurmaModel> subTurmas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AlunoModel> getDiscentes() {
        return discentes;
    }

    public void setDiscentes(List<AlunoModel> discentes) {
        this.discentes = discentes;
    }

    public List<ProfessorModel> getDoscente() {
        return doscentes;
    }

    public void setDoscente(List<ProfessorModel> doscentes) {
        this.doscentes = doscentes;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public List<AvaliacaoModel> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoModel> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
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

    public List<FrequenciaModel> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(List<FrequenciaModel> frequencia) {
        this.frequencia = frequencia;
    }

    public CronogramaModel getCronograma() {
        return cronograma;
    }

    public void setCronograma(CronogramaModel cronograma) {
        this.cronograma = cronograma;
    }

    public List<SubTurmaModel> getSubTurmas() {
        return subTurmas;
    }

    public void setSubTurmas(List<SubTurmaModel> subTurmas) {
        this.subTurmas = subTurmas;
    }
    
    
}
