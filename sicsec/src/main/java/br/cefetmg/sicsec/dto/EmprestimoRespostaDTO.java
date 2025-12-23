package br.cefetmg.sicsec.dto;

import java.util.Date;

public class EmprestimoRespostaDTO {
    private Long id;
    private LivroResumoDTO livro;
    private UsuarioResumoDTO usuario;
    private Date data;
    private String reserva;
    private Integer duracao;
    
    public EmprestimoRespostaDTO() {
    }

    public EmprestimoRespostaDTO(Long id, LivroResumoDTO livro, UsuarioResumoDTO usuario, Date data, String reserva, Integer duracao) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.data = data;
        this.reserva = reserva;
        this.duracao = duracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LivroResumoDTO getLivro() {
        return livro;
    }

    public void setLivro(LivroResumoDTO livro) {
        this.livro = livro;
    }

    public UsuarioResumoDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResumoDTO usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}