package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.cefetmg.sicsec.Model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    Resposta findByTentativaId(Long tentativaId);

    Integer countByAtividadeIdAndTentativaId(Long atividadeId, Long tentativaId);
}
