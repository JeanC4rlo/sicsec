package br.cefetmg.sicsec.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.StatusAtividade;

public interface StatusAtividadeRepository extends JpaRepository<StatusAtividade, Long> {
    long countByAtividade_Id(Long atividadeId);

    Optional<StatusAtividade> findTopByAtividade_IdOrderByNumTentativaDesc(Long atividadeId);
}
