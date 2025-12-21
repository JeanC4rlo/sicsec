package br.cefetmg.sicsec.dto;

public record DesempenhoComDadosAlunoDTO(
        Long desempenhoId,
        String nomeAluno,
        String turmaAluno,
        String nomeAtividade,
        Integer numTentativa,
        String tipoAtividade,
        Double notaAluno,
        Double valorAtividade) {
}
