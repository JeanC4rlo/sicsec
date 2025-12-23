package br.cefetmg.sicsec.dto;

public class LivroResumoDTO {
    private Long id;
    private String titulo;
    private String codigo;
    
    public LivroResumoDTO() {
    }

    public LivroResumoDTO(Long id, String titulo, String codigo) {
        this.id = id;
        this.titulo = titulo;
        this.codigo = codigo;
    }

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}