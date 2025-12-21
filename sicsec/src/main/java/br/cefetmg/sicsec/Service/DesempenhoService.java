package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.DesempenhoRepository;
import br.cefetmg.sicsec.Repository.TentativaRepository;
import br.cefetmg.sicsec.Repository.Usuarios.AlunoRepo;
import br.cefetmg.sicsec.dto.DesempenhoDTO;

@Service
public class DesempenhoService {
    @Autowired
    DesempenhoRepository desempenhoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    TentativaRepository tentativaRepository;

    @Autowired
    AlunoRepo alunoRepository;

    public Double getNotaTentativa(Long tentativaId) {
        Desempenho desempenho = desempenhoRepository.findByTentativaId(tentativaId);
        return desempenho.getNota();
    }

    public Double getMaiorNotaAtividade(Long atividadeId, Long alunoId) {
        Double nota = null;
        Desempenho desempenho = desempenhoRepository.findTopByAtividadeIdAndAlunoIdOrderByNotaDesc(atividadeId, alunoId);
        if(desempenho != null)
            nota = desempenho.getNota();
        return nota;
    }

    public Desempenho getDesempenho(Long desempenhoId) {
        return desempenhoRepository.findById(desempenhoId).orElseThrow();
    }

    public List<DesempenhoDTO> getListaDesempenhos() {
        return desempenhoRepository.findAll()
                .stream()
                .map(desempenho -> new DesempenhoDTO(
                        desempenho.getId(),
                        desempenho.getAluno().getMatricula().getNome(),
                        desempenho.getAtividade().getNome(),
                        desempenho.getNota(),
                        desempenho.getAtividade().getValor(),
                        desempenho.getAtividade().getTipo(),
                        desempenho.getResposta().getTextoRedacao(),
                        desempenho.getResposta().getId(),
                        desempenho.getResposta().getArquivoId(),
                        desempenho.getTentativa().getNumTentativa()))
                .toList();
    }

    public Desempenho salvarDesempenho(Resposta resposta, Aluno aluno) {
        return desempenhoRepository.save(popularDesempenho(resposta, aluno));
    }

    public Desempenho salvarDesempenho(Resposta resposta, Double nota, Aluno aluno) {
        return desempenhoRepository.save(popularDesempenho(resposta, nota, aluno));
    }

    private Desempenho popularDesempenho(Resposta resposta, Aluno aluno) {
        Desempenho desempenho = new Desempenho();

        desempenho.setAluno(aluno);
        desempenho.setAtividade(resposta.getAtividade());
        desempenho.setCorrigido(false);
        desempenho.setNota(null);
        desempenho.setResposta(resposta);
        desempenho.setTentativa(resposta.getTentativa());
        return desempenho;
    }

    private Desempenho popularDesempenho(Resposta resposta, Double nota, Aluno aluno) {
        Desempenho desempenho = popularDesempenho(resposta, aluno);
        desempenho.setCorrigido(true);
        desempenho.setNota(nota);
        return desempenho;
    }

    public Desempenho atribuirNota(Long desempenhoId, Double nota) {
        Desempenho desempenho = desempenhoRepository.findById(desempenhoId).orElseThrow();
        desempenho.setNota(nota);
        desempenho.setCorrigido(true);
        return desempenhoRepository.save(desempenho);
    }
}
