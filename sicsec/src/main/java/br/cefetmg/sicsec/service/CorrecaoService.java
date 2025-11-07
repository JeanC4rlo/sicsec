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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class CorrecaoService {
    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void corrigir(Resposta resposta) throws IOException {

        Optional<Atividade> atividadeOpt = atividadeRepository.findById(resposta.getAtividade().getId());
        if (atividadeOpt.isEmpty())
            return;
        Atividade atividade = atividadeOpt.get();
        System.out.println("JSON de questoes: " + atividade.getQuestoes());
        List<Questao> listaQuestoes = objectMapper.readValue(
                atividade.getQuestoes(),
                new TypeReference<List<Questao>>() {
                });
        Questao questao = listaQuestoes.get(resposta.getNumQuestao());
        System.out.println("Questão carregada: " + objectMapper.writeValueAsString(questao));

        Integer correta = listaQuestoes.get(resposta.getNumQuestao()).getIdxCorreta();
        Integer marcada = resposta.getAlternativaMarcada();
        System.out.println("Lista XD: " + correta + " " + marcada);
        if (correta.equals(marcada)) {
            resposta.setCorreta(true);
            return;
        }
        resposta.setCorreta(false);
    }
}
