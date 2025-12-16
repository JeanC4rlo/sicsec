/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Biblioteca;

import br.cefetmg.sicsec.Model.Util.Enum.Reserva;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import java.util.Date;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

/**
 *          
 * @author davig
 */

@Entity
public class Emprestimo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date data;
    private int duracao;
    
    //Reserva.EMPRESTIMO = empréstimo em curso
    //Reserva.RESERVA = reserva para empréstimo futuro
    //Reserva.HISTORICO = empréstimo já terminado
    @Enumerated(EnumType.STRING)
    private Reserva reserva;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario mortuario; //Quem fez o emprestimo

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Usuario getMortuario() {
        return mortuario;
    }

    public void setMortuario(Usuario mortuario) {
        this.mortuario = mortuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    
    
}
