package br.cefetmg.sicsec.Model;

import java.time.LocalDateTime;

public class QuestionarioEnviado extends AtividadeEnviada {
    private Integer numDaTentativa;
    private LocalDateTime horarioEnvio;
    private String respostas;
}
