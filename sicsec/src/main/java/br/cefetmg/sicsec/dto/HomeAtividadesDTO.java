package br.cefetmg.sicsec.dto;

public class HomeAtividadesDTO {
    private Long id;
    private String nome;
    private String tipo;
    private String valor;
    private String dataEncerramento;
    private String horaEncerramento;

    public HomeAtividadesDTO(Long id, String nome, String tipo, String valor, String dataEncerramento, String horaEncerramento) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.dataEncerramento = dataEncerramento;
        this.horaEncerramento = horaEncerramento;
    }

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
}
