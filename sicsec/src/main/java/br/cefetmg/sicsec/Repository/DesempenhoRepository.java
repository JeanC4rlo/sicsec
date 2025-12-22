package br.cefetmg.sicsec.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Model.Tentativa;

public interface DesempenhoRepository extends JpaRepository<Desempenho, Long> {
    Desempenho findByTentativaId(Long tentativaId);

    Desempenho findTopByAtividadeIdAndAlunoIdOrderByNotaDesc(Long atividadeId, Long alunoId);

    Optional<Desempenho> findByResposta(Resposta resposta);

    Optional<Desempenho> findByTentativa(Tentativa tentativa);

}
