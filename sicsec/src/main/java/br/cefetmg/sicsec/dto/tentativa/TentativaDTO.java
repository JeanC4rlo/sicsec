package br.cefetmg.sicsec.dto.tentativa;

public record TentativaDTO(
        Long id,
        Long atividadeId,
        Long alunoId,
        String horarioInicio,
        Integer tempoRestante,
        Integer numTentativa,
        Boolean aberta) {
}
