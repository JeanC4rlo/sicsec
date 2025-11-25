package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.Desempenho;

public interface DesempenhoRepository extends JpaRepository<Desempenho, Long> {
}
