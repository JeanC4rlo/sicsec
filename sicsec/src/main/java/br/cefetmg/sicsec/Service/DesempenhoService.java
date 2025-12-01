package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Resposta;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.DesempenhoRepository;
import br.cefetmg.sicsec.Repository.TentativaRepository;
import br.cefetmg.sicsec.Repository.Usuarios.AlunoRepo;
import br.cefetmg.sicsec.dto.DadosRespostaAlunoDTO;

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

    public Desempenho salvarDesempenho(Resposta resposta, Usuario aluno) {
        return desempenhoRepository.save(popularDesempenho(resposta, aluno));
    }

    public Desempenho salvarDesempenho(Resposta resposta, double nota, Usuario aluno) {
        return desempenhoRepository.save(popularDesempenho(resposta, nota, aluno));
    }

    private Desempenho popularDesempenho(Resposta resposta, Usuario aluno) {
        Desempenho desempenho = new Desempenho();

        desempenho.setAluno(aluno);
        desempenho.setAtividade(resposta.getAtividade());
        desempenho.setCorrigido(false);
        desempenho.setNota(null);
        desempenho.setResposta(resposta);
        desempenho.setTentativa(resposta.getTentativa());
        return desempenho;
    }

    private Desempenho popularDesempenho(Resposta resposta, double nota, Usuario aluno) {
        Desempenho desempenho = popularDesempenho(resposta, aluno);
        desempenho.setCorrigido(true);
        desempenho.setNota(nota);
        return desempenho;
    }

    public Desempenho atribuirNota(Long desempenhoId, Double nota) {
        Desempenho desempenho = desempenhoRepository.findById(desempenhoId).orElseThrow();
        desempenho.setNota(nota);
        return desempenhoRepository.save(desempenho);
    }
}
