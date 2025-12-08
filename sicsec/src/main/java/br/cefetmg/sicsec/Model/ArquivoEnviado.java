package br.cefetmg.sicsec.Model;

public class ArquivoEnviado extends AtividadeEnviada {
    private String nomeArquivo;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
