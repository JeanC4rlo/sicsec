package br.cefetmg.sicsec.dto;

public record DadosRespostaAlunoDTO(
    Long desempenhoId,
    Double valorAtividade,
    String tipoAtividade,
    String redacao,
    String nomeArquivo
) {}
