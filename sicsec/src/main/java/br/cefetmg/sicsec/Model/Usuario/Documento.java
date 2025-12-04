/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario;

import java.util.List;
import com.fasterxml.jackson.annotation.*;

import br.cefetmg.sicsec.Model.Util.Enum.StatusDocumento;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author davig
 */

@Entity
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 512)
    private String conteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id")
    private Usuario criador;

    @Enumerated(EnumType.STRING)
    private StatusDocumento status;

    @Temporal(TemporalType.DATE)
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracao;

    @JsonManagedReference
    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assinatura> assinaturas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setStatus(StatusDocumento status) {
        this.status = status;
    }

    public StatusDocumento getStatus() {
        return status;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Assinatura> getAssinaturas() {
        return assinaturas;
    }

    public void setAssinaturas(List<Assinatura> assinaturas) {
        this.assinaturas = assinaturas;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }
}