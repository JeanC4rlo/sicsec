package br.cefetmg.sicsec.dto.atividade;

public record AtividadeHomeDTO(
        Long id,
        String nome,
        String tipo,
        Double valor,
        Double nota,
        String dataEncerramento,
        String horaEncerramento,
        String disciplina,
        String nomeProfessor,
        Integer numTentativasRestantes) {
}
