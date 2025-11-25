package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.AlternativaMarcada;
import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.RespostaRepository;

@Service
public class CalculoNotaService {
    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    private DesempenhoService desempenhoService;

    public double calcular(Long tentativaId) {
        Resposta resposta = respostaRepository.findByTentativaId(tentativaId);
        Atividade atividade = resposta.getAtividade();

        long acertos = resposta.getAlternativasMarcadas()
                .stream()
                .filter(AlternativaMarcada::getEstaCorreta)
                .count();
        int numQuestoes = resposta.getAlternativasMarcadas().size();
        double valor = atividade.getValor();
        double nota = ((double) acertos / numQuestoes) * valor;

        desempenhoService.salvarDesempenhoQuestionario(resposta, nota);

        return nota;
    }
}
