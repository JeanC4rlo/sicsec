package br.cefetmg.sicsec.dto.atividade;

import br.cefetmg.sicsec.dto.DisciplinaDTO;

public record AtividadeEmTelaHomepageDTO(
        Long id,
        String nome,
        String tipo,
        DisciplinaDTO disciplina,
        Double valor,
        String dataEncerramento,
        String horaEncerramento) {}