package br.cefetmg.sicsec.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Boletim;
import br.cefetmg.sicsec.Model.Util.Enum.Situacao;
import br.cefetmg.sicsec.Repository.BoletimRepository;

@Service
public class BoletimService {

    @Autowired
    BoletimRepository boletimRepository;

    Boletim getOuCriarBoletim(Aluno aluno, int anoAtual) {
        Optional<Boletim> optBoletim =  boletimRepository.findByAluno_IdAndAnoLetivo(aluno.getId(), anoAtual);
        Boletim boletim;
        if(optBoletim.isEmpty()) {
            boletim = new Boletim();
            boletim.setAluno(aluno);
            boletim.setAnoLetivo(anoAtual);
            boletim.setSituacaoDoAno(Situacao.MATRICULADO);
            boletim = boletimRepository.save(boletim);
        }
        else {
            boletim = optBoletim.get();
        }
        return boletim;
    }
}