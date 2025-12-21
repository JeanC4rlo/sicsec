package br.cefetmg.sicsec.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.cefetmg.sicsec.Model.Atividade;
import br.cefetmg.sicsec.Model.TempoDuracao;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Repository.AtividadeRepository;
import br.cefetmg.sicsec.Repository.Usuarios.AlunoRepo;
import br.cefetmg.sicsec.Repository.Usuarios.ProfessorRepo;
import br.cefetmg.sicsec.dto.HomeAtividadesDTO;
import br.cefetmg.sicsec.dto.TempoDuracaoDTO;
import br.cefetmg.sicsec.dto.atividade.AtividadeAlunoDTO;
import br.cefetmg.sicsec.dto.atividade.AtividadeCreateDTO;
import br.cefetmg.sicsec.dto.atividade.AtividadeHomeDTO;
import br.cefetmg.sicsec.dto.atividade.AtividadeResumoDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private TurmaService turmaService;

    @Autowired
    private TentativaService tentativaService;

    @Autowired
    private DesempenhoService desempenhoService;

    @Autowired
    private ProfessorRepo professorRepository;

    @Autowired
    private AlunoRepo alunoRepository;

    public Atividade getAtividade(Long atividadeId) {
        return atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada"));
    }

    public AtividadeAlunoDTO getAtividadeDTO(Long atividadeId) {
        return toDTO(atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada")));
    }

    @Transactional
    public void salvarAtividade(AtividadeCreateDTO dto, MultipartFile[] arquivos,
            Usuario usuario)
            throws IOException {
        Atividade atividade = toEntity(dto);

        Professor professor = professorRepository.findById(usuario.getMatricula().getId())
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));
        ;

        atividade.setProfessor(professor);

        Atividade nova = atividadeRepository.save(atividade);
        if (arquivos != null && arquivos.length > 0 && !arquivos[0].isEmpty()) {
            arquivoService.salvarListaArquivos(nova, arquivos);
            nova = atividadeRepository.save(nova);
        }
    }

    public List<AtividadeResumoDTO> ListarAtividades() {
        return atividadeRepository.findAll().stream()
                .map(a -> new AtividadeResumoDTO(
                        a.getId(),
                        a.getNome(),
                        a.getTipo(),
                        a.getValor(),
                        a.getDataEncerramento(),
                        a.getDataCriacao()))
                .toList();
    }

    public List<HomeAtividadesDTO> ListarAtividadesHomeAtividadeDTO() {
        LocalDate hoje = LocalDate.now();

        return atividadeRepository.findAll().stream()
                .filter(a -> {
                    LocalDate dataEncerramento = LocalDate.parse(a.getDataEncerramento());
                    long diasRestantes = ChronoUnit.DAYS.between(hoje, dataEncerramento);
                    return diasRestantes > -3;
                })
                .map(a -> new HomeAtividadesDTO(
                        a.getId(),
                        a.getNome(),
                        a.getTipo(),
                        a.getValor(),
                        a.getDataEncerramento(),
                        a.getHoraEncerramento()))
                .toList();
    }

    public List<AtividadeHomeDTO> ListarAtividadesDTO(Usuario usuario) {
        Aluno aluno = alunoRepository.findById(usuario.getMatricula().getId())
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));

        String dataLimite = LocalDate.now().minusDays(2).toString();

        return atividadeRepository
                .findAtividadesVisiveisAluno(dataLimite, aluno.getTurmas())
                .stream()
                .map(a -> {
                    String nomeProfessor = a.getProfessor().getMatricula().getNome();
                    int tentativasRestantes = a.getTentativas() - tentativaService.getNumTentativasFeitas(a.getId());
                    Double nota = desempenhoService.getMaiorNotaAtividade(a.getId(), aluno.getId());

                    return new AtividadeHomeDTO(
                            a.getId(),
                            a.getNome(),
                            a.getTipo(),
                            a.getValor(),
                            nota,
                            a.getDataEncerramento(),
                            a.getHoraEncerramento(),
                            null,
                            nomeProfessor,
                            tentativasRestantes);
                })
                .toList();

    }

    private Atividade toEntity(AtividadeCreateDTO dto) {
        Atividade atividade = new Atividade();
        atividade.setNome(dto.nome());
        atividade.setTipo(dto.tipo());
        atividade.setValor(dto.valor());
        atividade.setDataEncerramento(dto.dataEncerramento());
        atividade.setHoraEncerramento(dto.horaEncerramento());
        atividade.setDataCriacao(dto.dataCriacao());
        atividade.setEnunciado(dto.enunciado());
        atividade.setQuestoes(dto.questoes());
        atividade.setTentativas(dto.tentativas());
        if (dto.tempoDuracao() != null)
            atividade.setTempoDuracao(
                    new TempoDuracao(dto.tempoDuracao().getHoras(), dto.tempoDuracao().getMinutos()));
        else
            atividade.setTempoDuracao(null);
        atividade.setTipoTimer(dto.tipoTimer());
        atividade.setTurmas(turmaService.listarTurmasPorId(dto.turmas()));
        return atividade;
    }

    private AtividadeAlunoDTO toDTO(Atividade atividade) {
        TempoDuracaoDTO tempoDuracaoDTO;
        if (atividade.getTempoDuracao() != null)
            tempoDuracaoDTO = new TempoDuracaoDTO(atividade.getTempoDuracao().getHoras(),
                    atividade.getTempoDuracao().getMinutos());
        else
            tempoDuracaoDTO = null;
        return new AtividadeAlunoDTO(
                atividade.getId(),
                atividade.getNome(),
                atividade.getTipo(),
                atividade.getValor(),
                atividade.getDataEncerramento(),
                atividade.getHoraEncerramento(),
                atividade.getDataCriacao(),
                atividade.getEnunciado(),
                atividade.getQuestoes(),
                atividade.getTentativas(),
                tempoDuracaoDTO,
                atividade.getTipoTimer());
    }
}
