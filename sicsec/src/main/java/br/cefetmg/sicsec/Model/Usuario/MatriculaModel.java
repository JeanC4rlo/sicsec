/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario;

import br.cefetmg.sicsec.Model.Util.CPF;
import jakarta.persistence.*;

/**
 *
 * @author davig
 */

@Entity

public class MatriculaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroMatricula;
    
    @Embedded
    private CPF cpf;

    @Column(nullable = false, length=255)
    private String nome;

    @Column(nullable = false, unique = true, length=255)
    private String email;

    @Column(length = 15)
    private String telefone;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dados_bancarios_id")
    private DadosBancariosModel dadosBancarios;

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(Long numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public DadosBancariosModel getDadosBancarios() {
        return dadosBancarios;
    }

    public void setDadosBancarios(DadosBancariosModel dadosBancarios) {
        this.dadosBancarios = dadosBancarios;
    }
    
}
