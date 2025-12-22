package br.cefetmg.sicsec.dto.atividade;

public record AtividadeResumoDTO(
        Long id,
        String nome,
        String tipo,
        Double valor,
        String dataEncerramento,
        String dataCriacao) {
}