package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.DesempenhoRepository;
import br.cefetmg.sicsec.Repository.TentativaRepository;
import br.cefetmg.sicsec.dto.DadosRespostaAlunoDTO;

@Service
public class DesempenhoService {
    @Autowired
    DesempenhoRepository desempenhoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    TentativaRepository tentativaRepository;

    public double getNota(Long tentativaId) {
        Desempenho desempenho = desempenhoRepository.findByTentativaId(tentativaId);
        return desempenho.getNota();
    }

    public Desempenho getDesempenho(Long desempenhoId) {
        return desempenhoRepository.findById(desempenhoId).orElseThrow();
    }

    public List<DadosRespostaAlunoDTO> getListaDesempenhos() {
        return desempenhoRepository.findAll()
                .stream()
                .map(desempenho -> new DadosRespostaAlunoDTO(
                        desempenho.getId(),
                        desempenho.getAtividade().getValor(),
                        desempenho.getAtividade().getTipo(),
                        desempenho.getResposta().getTextoRedacao(),
                        desempenho.getResposta().getNomeArquivo()))
                .toList();
    }

    public void salvarDesempenhoQuestionario(Resposta resposta, double nota) {
        Desempenho desempenho = new Desempenho();
        Atividade atividade = resposta.getAtividade();
        Tentativa tentativa = resposta.getTentativa();

        desempenho.setAtividade(atividade);
        desempenho.setTentativa(tentativa);
        desempenho.setResposta(resposta);
        desempenho.setNota(nota);
        desempenho.setCorrigido(true);

        desempenhoRepository.save(desempenho);
    }

    public void salvarDesempenhoRedacao(Resposta resposta) {
        Desempenho desempenho = new Desempenho();

        desempenho.setAtividade(resposta.getAtividade());
        desempenho.setTentativa(null);
        desempenho.setResposta(resposta);
        desempenho.setNota(null);
        desempenho.setCorrigido(false);

        desempenhoRepository.save(desempenho);
    }

    public void salvarDesempenhoEnvioArquivo(Resposta resposta) {
        Desempenho desempenho = new Desempenho();

        desempenho.setAtividade(resposta.getAtividade());
        desempenho.setTentativa(null);
        desempenho.setResposta(resposta);
        desempenho.setNota(null);
        desempenho.setCorrigido(false);

        desempenhoRepository.save(desempenho);
    }

    public Desempenho atribuirNota(Long desempenhoId, Double nota) {
        Desempenho desempenho = desempenhoRepository.findById(desempenhoId).orElseThrow();
        desempenho.setNota(nota);
        return desempenhoRepository.save(desempenho);
    }
}
