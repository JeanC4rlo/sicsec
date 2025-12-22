package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long>{
    
}
