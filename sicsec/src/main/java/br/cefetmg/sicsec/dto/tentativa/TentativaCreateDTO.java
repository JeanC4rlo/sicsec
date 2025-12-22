package br.cefetmg.sicsec.dto.tentativa;

public record TentativaCreateDTO(
        Long atividadeId,
        String horarioInicio,
        Integer tempoRestante,
        Integer numTentativa,
        Boolean aberta) {
}
