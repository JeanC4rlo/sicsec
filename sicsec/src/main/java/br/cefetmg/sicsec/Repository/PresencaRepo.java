package br.cefetmg.sicsec.Repository;

import br.cefetmg.sicsec.Model.Curso.Turma.presenca.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @author davig
 */
@Repository
public interface PresencaRepo extends JpaRepository<Presenca, Long> {
    
    List<Presenca> findByDiscenteId(Long alunoId);

}