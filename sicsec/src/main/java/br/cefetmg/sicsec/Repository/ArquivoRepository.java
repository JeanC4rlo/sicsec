package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.cefetmg.sicsec.Model.Arquivo;
import br.cefetmg.sicsec.Model.FileOwnerTypes;

import java.util.List;



public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    List<Arquivo> findByTipoDonoArquivoAndDonoId(FileOwnerTypes tipoDonoArquivo, Long donoId);
}