package br.cefetmg.sicsec.Model;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FileOwnerTypes tipoDonoArquivo;

    private Long donoId;

    private String nomeOriginal;
    private String nomeSalvo;
    private ZonedDateTime dataUpload;
    private Long tamanho;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileOwnerTypes getTipoDonoArquivo() {
        return tipoDonoArquivo;
    }

    public void setTipoDonoArquivo(FileOwnerTypes tipoDonoArquivo) {
        this.tipoDonoArquivo = tipoDonoArquivo;
    }

    public Long getDonoId() {
        return donoId;
    }

    public void setDonoId(Long donoId) {
        this.donoId = donoId;
    }

    public String getNomeOriginal() {
        return nomeOriginal;
    }

    public void setNomeOriginal(String nomeOriginal) {
        this.nomeOriginal = nomeOriginal;
    }

    public String getNomeSalvo() {
        return nomeSalvo;
    }

    public void setNomeSalvo(String nomeSalvo) {
        this.nomeSalvo = nomeSalvo;
    }

    public ZonedDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(ZonedDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

}
