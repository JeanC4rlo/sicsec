package br.cefetmg.sicsec.Model;

import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documentos")
public class DocumentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String conteudo;

    private LocalDateTime dataCriacao;

    @ManyToMany
    @JoinTable(
        name = "documento_assinantes",
        joinColumns = @JoinColumn(name = "documento_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<UsuarioModel> assinantes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "documento_datas_assinaturas", joinColumns = @JoinColumn(name = "documento_id"))
    @Column(name = "data_assinatura")
    private List<LocalDateTime> datasAssinaturas = new ArrayList<>();

    public Long getId() {
        return id;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<UsuarioModel> getAssinantes() {
        return assinantes;
    }

    public void setAssinantes(List<UsuarioModel> assinantes) {
        this.assinantes = assinantes;
    }

    public List<LocalDateTime> getDatasAssinaturas() {
        return datasAssinaturas;
    }

    public void setDatasAssinaturas(List<LocalDateTime> datasAssinaturas) {
        this.datasAssinaturas = datasAssinaturas;
    }
}
