package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Arquivo;
import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.RespostaRepository;
import br.cefetmg.sicsec.Repository.Usuarios.UsuarioRepo;
import br.cefetmg.sicsec.dto.DesempenhoDTO;
import jakarta.servlet.http.HttpSession;

@Service
public class RespostaService {
    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    DesempenhoService desempenhoService;

    @Autowired
    CorrecaoService correcaoService;

    @Autowired
    ArquivoService arquivoService;

    @Autowired
    UsuarioRepo usuarioRepository;

    @Autowired
    AtividadeService atividadeService;

    @Autowired
    TentativaService tentativaService;

    public Resposta getResposta(Long id) {
        return respostaRepository.findById(id).get();
    }

    public List<Resposta> getListRespostas() {
        return respostaRepository.findAll();
    }

    public Resposta salvarResposta(Resposta resposta, HttpSession session) {
        Usuario aluno = usuarioRepository.findById((Long) session.getAttribute("usuarioId")).get();
        resposta.setAluno(aluno);

        Atividade atividade = atividadeService.getAtividade(resposta.getAtividade().getId());
        resposta.setAtividade(atividade);

        Tentativa tentativa = tentativaService.getTentativa(resposta.getTentativa().getId());
        resposta.setTentativa(tentativa);
        
        Resposta nova = respostaRepository.save(resposta);
        if (nova.getAtividade().getTipo().equals("Questionário")) {
            double nota = correcaoService.corrigir(nova);
            desempenhoService.salvarDesempenho(nova, nota, aluno);
            return respostaRepository.save(nova); 
        }
        desempenhoService.salvarDesempenho(nova, aluno);
        return respostaRepository.save(nova);
    }

    public Resposta salvarRespostaComArquivo(Resposta resposta, MultipartFile arquivo, HttpSession session) throws IOException {
        if (arquivo == null || !arquivoService.validarArquivo(arquivo))
            throw new IOException();
        Usuario aluno = usuarioRepository.findById((Long) session.getAttribute("usuarioId")).get();
        resposta.setAluno(aluno);

        Resposta nova = respostaRepository.save(resposta);

        Arquivo arquivoEntidade = arquivoService.salvarArquivo(nova, arquivo);
        nova.setArquivoId(arquivoEntidade.getId());
        desempenhoService.salvarDesempenho(nova, aluno);
        return respostaRepository.save(nova);
    }

    public DesempenhoDTO getDesempenhoDTO(Long desempenhoId) {
        Desempenho desempenho = desempenhoService.getDesempenho(desempenhoId);
        Atividade atividade = desempenho.getAtividade();
        Resposta resposta = desempenho.getResposta();

        DesempenhoDTO dadosRespostaAlunoDTO = new DesempenhoDTO(
                desempenhoId,
                desempenho.getAluno().getMatricula().getNome(),
                atividade.getNome(),
                desempenho.getNota(),
                atividade.getValor(),
                atividade.getTipo(),
                resposta.getTextoRedacao(),
                resposta.getId(),
                resposta.getArquivoId());

        return dadosRespostaAlunoDTO;
    }
}
