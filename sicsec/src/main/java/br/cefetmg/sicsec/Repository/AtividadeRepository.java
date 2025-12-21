package br.cefetmg.sicsec.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
        @Query("""
                        SELECT a, t
                        FROM Atividade a
                        JOIN a.turmas t
                        WHERE t IN :turmas
                        AND a.dataEncerramento >= :dataLimite
                        """)
        List<Object[]> findAtividadesVisiveisAluno(
                        @Param("dataLimite") String dataLimite,
                        @Param("turmas") List<Turma> turmas);
}
