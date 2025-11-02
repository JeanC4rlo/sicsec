/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Service;


import br.cefetmg.sicsec.Model.Curso.Aula;
import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Repository.TurmaRepo;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Repository.*;
import java.util.*;

/**
 *
 * @author davig
 */

@Service
public class TurmaService {
    
    @Autowired
    private TurmaRepo turmaRepo;
    
    @Autowired
    private DisciplinaRepo disciplinaRepo;
    
    @Autowired
    private CursoRepo cursoRepo;
    
    @Autowired
    private UsuarioRepo usuarioRepo;
    
    @Autowired
    private AulaRepo aulaRepo;
    
    
    public Turma registrarTurma(Turma turma) {
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma registrarTurma(String nome, Long disciplinaId, Long cursoId, List<Long> discentesCpf, List<Long> doscentesCpf) {
        
        Disciplina disciplina = disciplinaRepo.findById(disciplinaId).orElseThrow(() -> new IllegalStateException("Disciplina Invalida"));
        Curso curso = cursoRepo.findById(cursoId).orElseThrow(() -> new IllegalStateException("Curso Invalido"));
        
        List<Aluno> discentes = new ArrayList<Aluno>();
        List<Professor> doscentes = new ArrayList<Professor>();
        
        for(Long cpf : discentesCpf) {
            Usuario u = usuarioRepo.findByCpf(cpf);
            if (u instanceof Aluno a) discentes.add(a);
            else throw new IllegalStateException("Cpf de Aluno Invalido");
        }
        
        for(Long cpf : doscentesCpf) {
            Usuario u = usuarioRepo.findByCpf(cpf);
            if (u instanceof Professor p) doscentes.add(p);
            else throw new IllegalStateException("Cpf de Professor Invalido");
        }
        
        Turma turma = new Turma(nome, disciplina, curso, discentes, doscentes);
        
        return registrarTurma(turma);
        
    }
    
    public Turma registrarTurma(String nome, Long disciplinaId, Long cursoId) {
        
        Disciplina disciplina = disciplinaRepo.findById(disciplinaId).orElseThrow(() -> new IllegalStateException("Disciplina Invalida"));
        Curso curso = cursoRepo.findById(cursoId).orElseThrow(() -> new IllegalStateException("Curso Invalido"));
        
        Turma turma = new Turma(nome, disciplina, curso);
        
        return registrarTurma(turma);
        
    }
    
    
    
    public Turma inserirAlunos(Turma turma, List<Long> discentesCpf) {
        
        List<Aluno> discentes = turma.getDiscentes();
        
        for(Long cpf : discentesCpf) {
            Usuario u = usuarioRepo.findByCpf(cpf);
            if (u instanceof Aluno a)
                if (!discentes.contains(a))
                    discentes.add(a);
            else throw new IllegalStateException("Cpf de Aluno Invalido");
        }
        
        turma.setDiscentes(discentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma inserirAlunos(Long turmaId, List<Long> discentesCpf) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return inserirAlunos(turma, discentesCpf);
        
    }
    
    
    
    public Turma inserirProfessor(Turma turma, Long doscenteCpf) {
        
        List<Professor> doscentes = turma.getDoscentes();
        
        Usuario u = usuarioRepo.findByCpf(doscenteCpf);
        if (u instanceof Professor p)
            if (!doscentes.contains(p))
                doscentes.add(p);
        else throw new IllegalStateException("Cpf de Professor Invalido");
        
        turma.setDoscentes(doscentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma inserirProfessor(Long turmaId, Long doscenteCpf) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return inserirProfessor(turma, doscenteCpf);
        
    }
    
    
    
    public Turma removerAluno(Turma turma, Long discenteCpf) {
        
        List<Aluno> discentes = turma.getDiscentes();
        
        Usuario u = usuarioRepo.findByCpf(discenteCpf);
        if (u instanceof Aluno a)
            if (!discentes.contains(a))
                discentes.remove(a);
        else throw new IllegalStateException("Cpf de Aluno Invalido");
        
        turma.setDiscentes(discentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma removerAluno(Long turmaId, Long discenteCpf) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return removerAluno(turma, discenteCpf);
        
    }
    
    
    
    public Turma removerProfessor(Turma turma, Long doscenteCpf) {
        
        List<Professor> doscentes = turma.getDoscentes();
        
        Usuario u = usuarioRepo.findByCpf(doscenteCpf);
        if (u instanceof Professor p)
            if (!doscentes.contains(p))
                doscentes.remove(p);
        else throw new IllegalStateException("Cpf de Professor Invalido");
        
        turma.setDoscentes(doscentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma removerProfessor(Long turmaId, Long doscenteCpf) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return removerProfessor(turma, doscenteCpf);
        
    }
    
    
    
    public Turma adicionarAula(Turma turma, Aula aula) {
        
        List<Aula> aulas = turma.getAulas();
        
        aulas.add(aula);
        
        turma.setAulas(aulas);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma adicionarAula(Long turmaId, Aula aula) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return adicionarAula(turma, aula);
        
    }
    
    public Turma removerAula(Turma turma, Aula aula) {
        
        List<Aula> aulas = turma.getAulas();
        
        aulas.remove(aula);
        
        turma.setAulas(aulas);
        
        aulaRepo.delete(aula);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma removerAula(Long turmaId, Long aulaId) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        Aula aula = aulaRepo.findById(aulaId).orElseThrow(() -> new IllegalStateException("Aula Invalida"));
        
        return removerAula(turma, aula);
        
    }
    
}