package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Repository.RespostaRepository;
import br.cefetmg.sicsec.dto.DadosRespostaAlunoDTO;

@Service
public class RespostaService {
    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    DesempenhoService desempenhoService;

    @Autowired
    CorrecaoService correcaoService;

    @Autowired
    ValidarArquivosService validarArquivosService;

    public Resposta getResposta(Long id) {
        return respostaRepository.findById(id).get();
    }

    public List<Resposta> getListRespostas() {
        return respostaRepository.findAll();
    }

    public Resposta salvarQuestionario(Resposta resposta) {
        double nota = correcaoService.corrigir(resposta);
        desempenhoService.salvarDesempenhoQuestionario(resposta, nota);
        return respostaRepository.save(resposta);
    }

    public Resposta salvarRedacao(Resposta resposta) {
        desempenhoService.salvarDesempenhoRedacao(resposta);
        return respostaRepository.save(resposta);
    }

    public Resposta salvarEnvioArquivo(Resposta resposta, MultipartFile arquivo) throws IOException {
        if(!validarArquivosService.validarArquivoUnico(arquivo)) throw new IOException();
        Resposta novaResposta = respostaRepository.save(resposta);
        System.out.println(novaResposta.getNomeArquivo());
        desempenhoService.salvarDesempenhoEnvioArquivo(novaResposta);
        return novaResposta;
    }

    public DadosRespostaAlunoDTO getDadosResposta(Long desempenhoId) {
        Desempenho desempenho = desempenhoService.getDesempenho(desempenhoId);
        Atividade atividade = desempenho.getAtividade();
        Resposta resposta = desempenho.getResposta();

        DadosRespostaAlunoDTO dadosRespostaAlunoDTO = new DadosRespostaAlunoDTO(
                desempenhoId,
                atividade.getValor(),
                atividade.getTipo(),
                resposta.getTextoRedacao(),
                resposta.getNomeArquivo());

        return dadosRespostaAlunoDTO;
    }
}
