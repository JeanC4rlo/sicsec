package br.cefetmg.sicsec.dto.atividade;

import java.util.List;

import br.cefetmg.sicsec.Model.TempoDuracao;

public record AtividadeCreateDTO(
    String nome,
    String tipo,
    Double valor,
    String dataEncerramento,
    String horaEncerramento,
    String dataCriacao,
    String enunciado,
    String questoes,
    Integer tentativas,
    TempoDuracao tempoDuracao,
    String tipoTimer,
    List<Long> turmas
) {}