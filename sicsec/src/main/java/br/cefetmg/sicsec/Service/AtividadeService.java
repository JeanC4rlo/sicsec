package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.dto.HomeAtividadesDTO;

@Service
public class AtividadeService {
    @Autowired
    AtividadeRepository atividadeRepository;

    public Atividade getAtividade(Long atividadeId) {
        return atividadeRepository.findById(atividadeId).get();
    }

    public List<Atividade> ListarAtividades() {
        return atividadeRepository.findAll();
    }

    public List<HomeAtividadesDTO> ListarAtividadesHomeAtividadeDTO() {
        return atividadeRepository.findAll().stream()
                .map(a -> new HomeAtividadesDTO(a.getId(), a.getNome(), a.getTipo(), a.getValor(),
                        a.getDataEncerramento(), a.getHoraEncerramento()))
                .toList();
    }
}
