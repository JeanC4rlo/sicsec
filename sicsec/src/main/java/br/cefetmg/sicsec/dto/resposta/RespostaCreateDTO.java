package br.cefetmg.sicsec.dto.resposta;

import java.util.List;

import br.cefetmg.sicsec.dto.AlternativaMarcadaDTO;

public record RespostaCreateDTO(
        Long atividadeId,
        Long tentativaId,
        List<AlternativaMarcadaDTO> alternativasMarcadas,
        String textoRedacao) {
}
