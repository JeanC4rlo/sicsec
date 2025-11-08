package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.RespostaRepository;

@Service
public class CalculoNotaService {
    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    public double calcular(Long atividadeId, Long statusAtividadeId) {
        Integer acertos = respostaRepository
                .countByAtividadeIdAndStatusAtividadeIdAndCorretaTrue(
                        atividadeId,
                        statusAtividadeId);

        Integer numQuestoes = respostaRepository
                .countByAtividadeIdAndStatusAtividadeId(
                        atividadeId,
                        statusAtividadeId);

        Atividade atividade = atividadeRepository.findById(atividadeId).get();

        return (acertos.doubleValue() / numQuestoes) * atividade.getValor();
    }
}
