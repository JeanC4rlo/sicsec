package br.cefetmg.sicsec.Repository;

import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioDAO extends JpaRepository<UsuarioModel, String> {
    //Busca usuários por tipo de matrícula (ESTUDANTE, PROFESSOR, ADMIN)
    List<UsuarioModel> findByCargo(Cargo cargo);

    //Busca usuários pela matrícula
    Optional<UsuarioModel> findByMatricula_NumeroMatricula(Long matricula);

    //Busca usuários pelo CPF
    List<UsuarioModel> findByMatricula_Cpf(Long cpf);
    
    //Busca usuários pelo nome contendo algo (LIKE %nome%)
    List<UsuarioModel> findByMatricula_NomeContaining(String nome);
}
