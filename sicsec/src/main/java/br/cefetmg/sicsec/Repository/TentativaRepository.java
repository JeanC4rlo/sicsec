package br.cefetmg.sicsec.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.Tentativa;

public interface TentativaRepository extends JpaRepository<Tentativa, Long> {
    long countByAtividade_Id(Long atividadeId);

    int countByAtividade_IdAndAbertaFalse(Long atividadeId);

    Optional<Tentativa> findTopByAtividade_IdOrderByNumTentativaDesc(Long atividadeId);

    Optional<Tentativa> findByAtividade_IdAndAbertaTrue(Long atividadeId);
}
