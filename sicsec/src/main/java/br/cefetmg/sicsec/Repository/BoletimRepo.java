package br.cefetmg.sicsec.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Boletim;
import java.util.List;
import java.util.Optional;


@Repository
public interface BoletimRepo extends JpaRepository<Boletim, Long>{

    List<Boletim> findByAluno(Aluno usuario);

    List<Boletim> findByAlunoId(@Param("aluno_id") Long id);

    Optional<Boletim> findByAluno_IdAndAnoLetivo(Long alunoId, int anoLetivo);

}
