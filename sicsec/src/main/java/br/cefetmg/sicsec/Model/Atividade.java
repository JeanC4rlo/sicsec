package br.cefetmg.sicsec.Model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String tipo;
    private Double valor;
    private String dataEncerramento;
    private String horaEncerramento;
    @Lob
    private String enunciado;
    private String questoes;
    private Integer tentativas;
    private String tempoDeDuracao;
    private String tipoTimer;
    @ElementCollection
    private List<String> nomesArquivos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(String dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getHoraEncerramento() {
        return horaEncerramento;
    }

    public void setHoraEncerramento(String horaEncerramento) {
        this.horaEncerramento = horaEncerramento;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getQuestoes() {
        return questoes;
    }

    public void setQuestoes(String questoes) {
        this.questoes = questoes;
    }

    public Integer getTentativas() {
        return tentativas;
    }

    public void setTentativas(Integer tentativas) {
        this.tentativas = tentativas;
    }

    public String getTempoDeDuracao() {
        return tempoDeDuracao;
    }

    public void setTempoDeDuracao(String tempoDeDuracao) {
        this.tempoDeDuracao = tempoDeDuracao;
    }

    public String getTipoTimer() {
        return tipoTimer;
    }

    public void setTipoTimer(String tipoTimer) {
        this.tipoTimer = tipoTimer;
    }

    public List<String> getNomesArquivos() {
        return nomesArquivos;
    }

    public void setNomesArquivos(List<String> nomesArquivos) {
        this.nomesArquivos = nomesArquivos;
    }

}
