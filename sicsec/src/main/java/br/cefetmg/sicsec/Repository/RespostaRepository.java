package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.cefetmg.sicsec.Model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    Integer countByAtividadeIdAndStatusAtividadeId(Long atividadeId, Long statusAtividadeId);

    Integer countByAtividadeIdAndStatusAtividadeIdAndCorretaTrue(Long atividadeId, Long statusAtividadeId);
}
