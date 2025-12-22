package br.cefetmg.sicsec.dto.atividade;

import br.cefetmg.sicsec.dto.DisciplinaDTO;

public record AtividadeEmTelaAtividadesDTO(
        Long id,
        String nome,
        String tipo,
        Double valor,
        Double nota,
        String dataEncerramento,
        String horaEncerramento,
        DisciplinaDTO disciplina,
        String nomeProfessor,
        Integer numTentativasRestantes) {
}
