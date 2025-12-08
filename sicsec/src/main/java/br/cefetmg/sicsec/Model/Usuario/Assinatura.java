/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario;

import com.fasterxml.jackson.annotation.*;

import br.cefetmg.sicsec.Model.Util.Enum.StatusAssinatura;
import jakarta.persistence.*;
import java.util.Date;

/**
 *
 * @author davig
 */
@Entity
@Table(name = "assinaturas")
public class Assinatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAssinatura;
    
    @Enumerated(EnumType.STRING)
    private StatusAssinatura status;
    
    public Assinatura() {
        this(null, null);
    }
    
    public Assinatura(Documento documento, Usuario usuario) {
        this.dataCriacao = new Date();
        this.status = StatusAssinatura.PENDENTE;
        this.documento = documento;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public StatusAssinatura getStatus() {
        return status;
    }

    public void setStatus(StatusAssinatura status) {
        this.status = status;
    }
    
    public boolean isPendente() {
        return StatusAssinatura.PENDENTE.equals(this.status);
    }
    
    public boolean isConfirmada() {
        return StatusAssinatura.CONFIRMADA.equals(this.status);
    }
    
    public boolean isRejeitada() {
        return StatusAssinatura.REJEITADA.equals(this.status);
    }
}