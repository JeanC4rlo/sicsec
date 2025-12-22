package br.cefetmg.sicsec.Dto;

import java.util.List;

public class DisciplinaDTO {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private String modalidade;
    private List<Long> professorIds;
    private List<Long> alunoIds;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
    public List<Long> getProfessorIds() { return professorIds; }
    public void setProfessorIds(List<Long> professorIds) { this.professorIds = professorIds; }
    public List<Long> getAlunoIds() { return alunoIds; }
    public void setAlunoIds(List<Long> alunoIds) { this.alunoIds = alunoIds; }
}