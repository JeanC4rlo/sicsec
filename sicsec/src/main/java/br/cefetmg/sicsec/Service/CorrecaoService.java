package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import br.cefetmg.sicsec.Model.AlternativaMarcada;
import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Questao;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Exceptions.CorrecaoException;

@Service
public class CorrecaoService {

    @Autowired
    private ObjectMapper objectMapper;

    public double corrigir(Resposta resposta) throws CorrecaoException {
        try {
            Atividade atividade = resposta.getAtividade();
            if (atividade == null) {
                throw new EntityNotFoundException("Atividade não encontrada");
            }
            System.out.println("teste 1.4");
            List<Questao> listaQuestoes = objectMapper.readValue(
                    atividade.getQuestoes(),
                    new TypeReference<List<Questao>>() {
                    });

            if (resposta.getAlternativasMarcadas() == null || resposta.getAlternativasMarcadas().isEmpty()) {
                throw new CorrecaoException("Nenhuma alternativa marcada foi encontrada");
            }
            System.out.println("teste 1.5");
            validarEMarcar(resposta.getAlternativasMarcadas(), listaQuestoes);
            return calcularNota(resposta.getAlternativasMarcadas(), atividade.getValor());

        } catch (IOException e) {
            throw new CorrecaoException("Erro ao processar os dados da atividade", e);
        }
    }

    private void validarEMarcar(List<AlternativaMarcada> alternativasMarcadas, List<Questao> questoes) {
        for (AlternativaMarcada alt : alternativasMarcadas) {
            if (alt.getNumQuestao() < 0 || alt.getNumQuestao() >= questoes.size()) {
                System.out.println("Exceção lançada");
                throw new CorrecaoException("Número da questão inválido: " + alt.getNumQuestao());
            }

            System.out.println("teste 1.6");
            Questao questao = questoes.get(alt.getNumQuestao());
            System.out.println("teste 1.7: " + questao.getIdxCorreta() + " " + alt.getAlternativa());
            boolean correta = questao.getIdxCorreta().equals(alt.getAlternativa());
            System.out.println("teste 1.8");
            alt.setCorreta(correta);
            System.out.println("teste 1.9");
        }
    }

    private double calcularNota(List<AlternativaMarcada> alternativasMarcadas, double valor) {
        long acertos = alternativasMarcadas.stream()
                .filter(AlternativaMarcada::isCorreta)
                .count();

        return ((double) acertos / alternativasMarcadas.size()) * valor;
    }
}
