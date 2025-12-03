package br.cefetmg.sicsec.dto;

public record DesempenhoDTO(
    Long desempenhoId,
    String nomeAluno,
    String nomeAtividade,
    Double notaAluno,
    Double valorAtividade,
    String tipoAtividade,
    String redacao,
    Long respostaId,
    Long arquivoId
) {}
