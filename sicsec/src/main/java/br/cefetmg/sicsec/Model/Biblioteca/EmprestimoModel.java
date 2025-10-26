/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Biblioteca;

import br.cefetmg.sicsec.Model.Util.Enum.Reserva;
import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import java.util.Date;

/**
 *
 * @author davig
 */
public class EmprestimoModel {
        
    private UsuarioModel mortuario; //Quem fez o emprestimo
    private LivroModel livro;
    private Date data;
    private int duracao;
    private Reserva reserva;

    public UsuarioModel getMortuario() {
        return mortuario;
    }

    public void setMortuario(UsuarioModel mortuario) {
        this.mortuario = mortuario;
    }

    public LivroModel getLivro() {
        return livro;
    }

    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    
    
    
}
