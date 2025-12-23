package br.cefetmg.sicsec.dto;

import java.util.Date;

public class EmprestimoDTO {
    private Long id;
    private String codigoLivro;
    private String tipo;
    private Date dataExpiracao;
    private Integer duracao;
    private Long matriculaUsuario;
    
    public EmprestimoDTO() {
    }

    public EmprestimoDTO(Long id, String codigoLivro, String tipo, Date dataExpiracao, Integer duracao, Long matriculaUsuario) {
        this.id = id;
        this.codigoLivro = codigoLivro;
        this.tipo = tipo;
        this.dataExpiracao = dataExpiracao;
        this.duracao = duracao;
        this.matriculaUsuario = matriculaUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(String codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Long getMatriculaUsuario() {
        return matriculaUsuario;
    }

    public void setMatriculaUsuario(Long matriculaUsuario) {
        this.matriculaUsuario = matriculaUsuario;
    }
}