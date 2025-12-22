package br.cefetmg.sicsec.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.cefetmg.sicsec.Model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    Resposta findByTentativaId(Long tentativaId);

    Integer countByAtividadeIdAndTentativaId(Long atividadeId, Long tentativaId);

    Optional<Resposta> findByAlunoIdAndAtividadeIdAndTentativaId(Long alunoId, Long atividadeId, Long tentativaId);
}
