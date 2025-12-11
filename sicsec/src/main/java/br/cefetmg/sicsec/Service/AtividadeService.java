package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.Usuarios.AlunoRepo;
import br.cefetmg.sicsec.Repository.Usuarios.ProfessorRepo;
import br.cefetmg.sicsec.dto.AtividadeDTO;
import br.cefetmg.sicsec.dto.HomeAtividadesDTO;
import br.cefetmg.sicsec.dto.Perfil;
import jakarta.servlet.http.HttpSession;

@Service
public class AtividadeService {
    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private TentativaService tentativaService;

    @Autowired
    private DesempenhoService desempenhoService;

    @Autowired
    private ProfessorRepo professorRepository;

    @Autowired
    private AlunoRepo alunoRepository;

    public Atividade getAtividade(Long atividadeId) {
        return atividadeRepository.findById(atividadeId).get();
    }

    public Atividade salvarAtividade(Atividade atividade, MultipartFile[] arquivos, HttpSession session)
            throws IOException {
        Usuario usuario = ((Perfil) session.getAttribute("perfilSelecionado")).getUsuario();
        Professor professor = professorRepository.findById(usuario.getMatricula().getId()).get();
        atividade.setProfessor(professor);
        Atividade nova = atividadeRepository.save(atividade);
        if (arquivos != null && arquivos.length > 0 && !arquivos[0].isEmpty()) {
            arquivoService.salvarListaArquivos(nova, arquivos);
            nova = atividadeRepository.save(nova);
        }
        return nova;
    }

    public List<Atividade> ListarAtividades() {
        return atividadeRepository.findAll();
    }

    public List<HomeAtividadesDTO> ListarAtividadesHomeAtividadeDTO() {
        return atividadeRepository.findAll().stream()
                .map(a -> new HomeAtividadesDTO(a.getId(), a.getNome(), a.getTipo(), a.getValor(),
                        a.getDataEncerramento(), a.getHoraEncerramento()))
                .toList();
    }

    public List<AtividadeDTO> ListarAtividadesDTO(HttpSession session) {
        Usuario usuario = ((Perfil) session.getAttribute("perfilSelecionado")).getUsuario();
        Aluno aluno = alunoRepository.findById(usuario.getMatricula().getId()).get();
        return atividadeRepository.findAll().stream()
                .map(a -> {
                    String nomeProfessor = a.getProfessor().getMatricula().getNome();
                    int numTentativasRestantes = a.getTentativas() - tentativaService.getNumTentativasFeitas(a.getId());
                    Double nota = desempenhoService.getMaiorNotaAtividade(a.getId(), aluno.getId());
                    return new AtividadeDTO(
                            a.getId(),
                            a.getNome(),
                            a.getTipo(),
                            a.getValor(),
                            nota,
                            a.getDataEncerramento(),
                            a.getHoraEncerramento(),
                            null,
                            nomeProfessor,
                            numTentativasRestantes);
                })
                .toList();
    }
}
