package br.cefetmg.sicsec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private String valor;
    private String dataEncerramento;
    private String horaEncerramento;
    private String questoes;
    private String tentativas;
    private String tempoDeDuracao;
    
    public Long getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public String getDataEncerramento() {
        return dataEncerramento;
    }

    public String getHoraEncerramento() {
        return horaEncerramento;
    }

    public String getQuestoes() {
        return questoes;
    }

    public String getTentativas() {
        return tentativas;
    }

    public String getTempoDeDuracao() {
        return tempoDeDuracao;
    }
}
