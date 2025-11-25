package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Questao;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Exceptions.CorrecaoException;

@Service
public class CorrecaoService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void corrigir(Resposta resposta) throws CorrecaoException {
        try {
            Atividade atividade = atividadeRepository.findById(resposta.getAtividade().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada"));

            List<Questao> listaQuestoes = objectMapper.readValue(
                    atividade.getQuestoes(),
                    new TypeReference<List<Questao>>() {
                    });

            resposta.getAlternativasMarcadas().forEach(alt -> {
                if (alt.getNumQuestao() < 0 || alt.getNumQuestao() >= listaQuestoes.size()) {
                    throw new CorrecaoException("Número da questão inválido");
                }

                Questao questao = listaQuestoes.get(alt.getNumQuestao());
                alt.setEstaCorreta(questao.getIdxCorreta().equals(alt.getAlternativa()));
            });

        } catch (IOException e) {
            throw new CorrecaoException("Erro ao processar os dados da atividade", e);
        }
    }
}
