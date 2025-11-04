package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {}
