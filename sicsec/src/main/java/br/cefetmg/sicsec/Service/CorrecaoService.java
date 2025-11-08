package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Questao;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import jakarta.persistence.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class CorrecaoService {
    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void corrigir(Resposta resposta) throws IOException, EntityNotFoundException {

        Optional<Atividade> atividadeOpt = atividadeRepository.findById(resposta.getAtividade().getId());

        if (atividadeOpt.isEmpty())
            throw new EntityNotFoundException();

        Atividade atividade = atividadeOpt.get();

        List<Questao> listaQuestoes = objectMapper.readValue(
                atividade.getQuestoes(),
                new TypeReference<List<Questao>>() {
                });

        Integer correta = listaQuestoes.get(resposta.getNumQuestao()).getIdxCorreta();
        Integer marcada = resposta.getAlternativaMarcada();
        resposta.setCorreta(correta.equals(marcada));
    }
}
