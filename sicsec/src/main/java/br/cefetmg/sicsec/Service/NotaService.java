package br.cefetmg.sicsec.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Desempenho;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Boletim;
import br.cefetmg.sicsec.Model.Usuario.Aluno.ComponenteCurricular;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Nota;
import br.cefetmg.sicsec.Model.Util.Enum.Aprovacao;
import br.cefetmg.sicsec.Model.Util.Enum.Bimestre;
import br.cefetmg.sicsec.Model.Util.Enum.Situacao;
import br.cefetmg.sicsec.Repository.BoletimRepository;
import br.cefetmg.sicsec.Repository.ComponenteCurricularRepository;
import br.cefetmg.sicsec.Repository.NotaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class NotaService {
    @Autowired
    NotaRepository notaRepository;

    @Autowired
    ComponenteCurricularRepository componenteCurricularRepository;

    @Autowired
    BoletimService boletimService;

    @Autowired
    ComponenteCurricularService componenteCurricularService;

    @Autowired
    BoletimRepository boletimRepository;

    @Transactional
    public void salvarNota(Desempenho desempenho, Aluno aluno) {
        System.out.println("Chegou");
        int anoAtual = LocalDate.now().getYear();
        Boletim boletim = boletimService.getOuCriarBoletim(aluno, anoAtual);
        System.out.println("criou/pegou boletim");
        componenteCurricularService.criarComponentesParaBoletim(boletim);
        System.out.println("criou/pegou CC");

        Disciplina disciplina = desempenho.getResposta().getDisciplina();
        ComponenteCurricular cc = componenteCurricularRepository
                .findByBoletim_Aluno_IdAndBoletim_AnoLetivoAndDisciplina_Id(
                        aluno.getId(), anoAtual, disciplina.getId())
                .orElseThrow(() -> new EntityNotFoundException("Componente Curricular não encontrado"));
System.out.println("treco pre nota");
        Nota nota = new Nota();
        nota.setDesempenho(desempenho);
        nota.setComponente(cc);
        nota.setBimestre(Bimestre.getByMonth(LocalDate.now().getMonthValue()));
        nota.setAvaliacao(desempenho.getAtividade().getNome());
        nota.setValor(desempenho.getNota());
        System.out.println("settou nota");
        notaRepository.save(nota);
        System.out.println("salvou nota");

        atualizarComponenteCurricular(cc);
        componenteCurricularRepository.save(cc);

        atualizarBoletim(boletim);
        boletimRepository.save(boletim);
    }

    private void atualizarBoletim(Boletim boletim) {
        List<ComponenteCurricular> ccs = boletim.getComponentes();
        if (ccs != null && !ccs.isEmpty()) {
            double soma = ccs.stream().mapToInt(ComponenteCurricular::getNotaFinal).sum();
            int mediaAno = (int) Math.round(soma / ccs.size());

            boletim.setSituacaoDoAno(mediaAno >= 60 ? Situacao.APROVADO : Situacao.MATRICULADO);
        }
    }

    private void atualizarComponenteCurricular(ComponenteCurricular cc) {
        List<Nota> notas = cc.getNotas();
        if (notas != null && !notas.isEmpty()) {
            double soma = notas.stream().mapToDouble(nota -> {
                return nota.getDesempenho().getAtividade().getValor();
            }).sum();
            int media = (int) Math.round(soma / notas.size());
            cc.setNotaFinal(media);
        } else {
            cc.setNotaFinal(0);
        }

        cc.setSituacao(cc.getNotaFinal() >= 60 ? Aprovacao.APROVADO : Aprovacao.REPROVADO);
    }
}