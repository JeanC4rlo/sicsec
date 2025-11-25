package br.cefetmg.sicsec.dto;

public record DesempenhoDTO(
        Long atividadeId,
        Long tentativaId,
        Long respostaId,
        Double nota,
        Boolean corrigido,
        String nomeAtividade,
        String tipoAtividade,
        Double valorAtividade) {
}
