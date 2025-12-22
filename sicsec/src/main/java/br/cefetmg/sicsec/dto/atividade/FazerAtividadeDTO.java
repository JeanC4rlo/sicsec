package br.cefetmg.sicsec.dto.atividade;

import br.cefetmg.sicsec.dto.TempoDuracaoDTO;

public record FazerAtividadeDTO(
        Long id,
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
        String tipoTimer) {
}