package br.cefetmg.sicsec.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Boletim;

public interface BoletimRepository extends JpaRepository<Boletim, Long> {
    Optional<Boletim> findByAluno_IdAndAnoLetivo(Long alunoId, int anoLetivo);
}
