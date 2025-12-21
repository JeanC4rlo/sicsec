package br.cefetmg.sicsec.dto.atividade;

import java.util.List;

import br.cefetmg.sicsec.dto.TempoDuracaoDTO;

public record AtividadeTurmasDTO(
        String nome,
        String tipo,
        Double valor,
        String dataEncerramento,
        String horaEncerramento,
        String dataCriacao,
        String enunciado,
        String questoes,
        Integer tentativas,
        TempoDuracaoDTO tempoDuracao,
        String tipoTimer,
        List<Long> turmas) {
}