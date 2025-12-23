package br.cefetmg.sicsec.dto;

public class UsuarioResumoDTO {
    private Long id;
    private String nome;
    private Long matricula;
    
    public UsuarioResumoDTO() {
    }

    public UsuarioResumoDTO(Long id, String nome, Long matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

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

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }
}