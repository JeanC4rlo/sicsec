package br.cefetmg.sicsec.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.cefetmg.sicsec.Model.Usuario.Aluno.ComponenteCurricular;

@Repository
public interface ComponenteCurricularRepository extends JpaRepository<ComponenteCurricular, Long> {
    Optional<ComponenteCurricular> findByBoletim_Aluno_IdAndBoletim_AnoLetivoAndDisciplina_Id(
            Long alunoId, int anoLetivo, Long disciplinaId);

            boolean existsByBoletimIdAndDisciplinaId(Long boletimId, Long disciplinaId);
}
