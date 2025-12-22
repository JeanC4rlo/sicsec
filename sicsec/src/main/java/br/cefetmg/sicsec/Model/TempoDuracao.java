package br.cefetmg.sicsec.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class TempoDuracao {
    private int horas;
    private int minutos;

    public TempoDuracao() {
    }

    public TempoDuracao(int horas, int minutos) {
        if (horas < 0 || minutos < 0 || minutos >= 60) {
            throw new IllegalArgumentException("Tempo inválido");
        }
        this.horas = horas;
        this.minutos = minutos;
    }

    public int getHoras() {
        return horas;
    }

    public int getMinutos() {
        return minutos;
    }
}
