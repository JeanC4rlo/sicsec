package br.cefetmg.sicsec.Model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class QuestionarioEnviado extends AtividadeEnviada {
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Tentativa numDaTentativa;

    private String respostas;

    public Tentativa getNumDaTentativa() {
        return numDaTentativa;
    }

    public void setNumDaTentativa(Tentativa numDaTentativa) {
        this.numDaTentativa = numDaTentativa;
    }

    public String getRespostas() {
        return respostas;
    }

    public void setRespostas(String respostas) {
        this.respostas = respostas;
    }
}
