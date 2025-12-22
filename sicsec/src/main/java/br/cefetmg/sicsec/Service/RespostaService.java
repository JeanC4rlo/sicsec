package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.cefetmg.sicsec.Model.AlternativaMarcada;
import br.cefetmg.sicsec.Model.Arquivo;
import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Model.Tentativa;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Repository.RespostaRepository;
import br.cefetmg.sicsec.Repository.Usuarios.AlunoRepo;
import br.cefetmg.sicsec.dto.AlternativaMarcadaDTO;
import br.cefetmg.sicsec.dto.DesempenhoDTO;
import br.cefetmg.sicsec.dto.resposta.RespostaCreateDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RespostaService {

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    DesempenhoService desempenhoService;

    @Autowired
    DisciplinaService disciplinaService;

    @Autowired
    CorrecaoService correcaoService;

    @Autowired
    ArquivoService arquivoService;

    @Autowired
    AlunoRepo alunoRepository;

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

    @Transactional
    public Resposta salvarOuAtualizarResposta(RespostaCreateDTO dto, Usuario usuario, MultipartFile arquivo)
            throws IOException {
        if (arquivo != null && !arquivoService.validarArquivo(arquivo))
            throw new IOException();

        Aluno aluno = alunoRepository.findById(usuario.getId())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        Atividade atividade = atividadeService.getAtividade(dto.atividadeId());
        Tentativa tentativa = tentativaService.getTentativa(dto.tentativaId());

        Optional<Resposta> opt = respostaRepository
                .findByAlunoIdAndAtividadeIdAndTentativaId(aluno.getId(), atividade.getId(),
                        tentativa.getId());

        Resposta resposta;
        if (opt.isPresent()) {
            System.out.println("achou existente");
            resposta = opt.get();
        } else {
            System.out.println("Criou");
            resposta = toEntity(dto);
            resposta.setAluno(aluno);
            System.out.println("DTO: " + dto.toString());
        }

        Resposta nova = respostaRepository.save(resposta);

        System.out.println("salvou");
        if (arquivo != null) {
            Arquivo arquivoEntidade = arquivoService.salvarArquivo(nova, arquivo);
            nova.setArquivoId(arquivoEntidade.getId());
            nova = respostaRepository.save(nova);
        }
        
        Double nota = null;
        if (nova.getAtividade().getTipo().equals("Questionário"))
            nota = correcaoService.corrigir(nova);

        desempenhoService.salvarDesempenho(nova, nota, aluno);

        return nova;
    }

    /*
     * @Transactional
     * public void salvarRespostaComArquivo(RespostaCreateDTO dto, MultipartFile
     * arquivo, Usuario usuario)
     * throws IOException {
     * if (arquivo == null || !arquivoService.validarArquivo(arquivo))
     * throw new IOException();
     * Resposta resposta = toEntity(dto);
     * 
     * Aluno aluno = alunoRepository.findById(usuario.getMatricula().getId()).get();
     * resposta.setAluno(aluno);
     * 
     * Resposta nova = respostaRepository.save(resposta);
     * 
     * Arquivo arquivoEntidade = arquivoService.salvarArquivo(nova, arquivo);
     * nova.setArquivoId(arquivoEntidade.getId());
     * desempenhoService.salvarDesempenho(nova, null, aluno);
     * respostaRepository.save(nova);
     * }
     */

    public DesempenhoDTO getDesempenhoDTO(Long desempenhoId) {
        Desempenho desempenho = desempenhoService.getDesempenho(desempenhoId);
        Atividade atividade = desempenho.getAtividade();
        Resposta resposta = desempenho.getResposta();
        Tentativa tentativa = desempenho.getTentativa();

        DesempenhoDTO dadosRespostaAlunoDTO = new DesempenhoDTO(
                desempenhoId,
                desempenho.getAluno().getMatricula().getNome(),
                atividade.getNome(),
                desempenho.getNota(),
                atividade.getValor(),
                atividade.getTipo(),
                resposta.getTextoRedacao(),
                resposta.getId(),
                resposta.getArquivoId(),
                tentativa.getNumTentativa());

        return dadosRespostaAlunoDTO;
    }

    private Resposta toEntity(RespostaCreateDTO dto) {
        Resposta resposta = new Resposta();
        resposta.setAtividade(atividadeService.getAtividade(dto.atividadeId()));
        resposta.setTentativa(tentativaService.getTentativa(dto.tentativaId()));
        resposta.setDisciplina(disciplinaService.getDisciplina(dto.disciplinaId()));
        resposta.setAlternativasMarcadas(toEntity(dto.alternativasMarcadas()));
        resposta.setTextoRedacao(dto.textoRedacao());
        resposta.setArquivoId(null);
        return resposta;
    }

    private List<AlternativaMarcada> toEntity(List<AlternativaMarcadaDTO> dtoList) {
        if (dtoList == null)
            return List.of();

        return dtoList.stream()
                .map(dto -> new AlternativaMarcada(
                        dto.numQuestao(),
                        dto.alternativa(),
                        dto.estaCorreta()))
                .toList();
    }

}
