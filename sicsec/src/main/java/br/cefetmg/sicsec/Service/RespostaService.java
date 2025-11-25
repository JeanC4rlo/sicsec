package br.cefetmg.sicsec.Service;

import java.io.IOException;

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
    ValidarArquivosService validarArquivosService;

    public Resposta getResposta(Long id) {
        return respostaRepository.findById(id).get();
    }

    public Resposta salvarRedacao(Resposta resposta) {
        desempenhoService.salvarDesempenhoRedacao(resposta);
        return respostaRepository.save(resposta);
    }

    public Resposta salvarEnvioArquivo(Resposta resposta, MultipartFile arquivo) throws IOException { //implementar check de arquivo
        if(!validarArquivosService.validarArquivoUnico(arquivo)) throw new IOException();
        Resposta novaResposta = respostaRepository.save(resposta);
        System.out.println(novaResposta.getNomeArquivo());
        desempenhoService.salvarDesempenhoEnvioArquivo(novaResposta);
        return novaResposta;
    }

    public DadosRespostaAlunoDTO getDadosRespostaAluno(Long desempenhoId) {
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
