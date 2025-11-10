/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Curso.Aula;
import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Repository.AulaRepo;
import br.cefetmg.sicsec.Repository.CursoRepo;
import br.cefetmg.sicsec.Repository.DisciplinaRepo;
import br.cefetmg.sicsec.Repository.TurmaRepo;
import br.cefetmg.sicsec.Repository.Usuarios.UsuarioRepo;
import jakarta.servlet.http.HttpServletRequest;

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
    
    public Turma registrarTurma(String nome, int anoLetivo, Long disciplinaId, Long cursoId, List<Long> discentesId, List<Long> doscentesId) {
        
        Disciplina disciplina = disciplinaRepo.findById(disciplinaId).orElseThrow(() -> new IllegalStateException("Disciplina Invalida"));
        Curso curso = cursoRepo.findById(cursoId).orElseThrow(() -> new IllegalStateException("Curso Invalido"));
        
        List<Aluno> discentes = new ArrayList<>();
        List<Professor> doscentes = new ArrayList<>();
        
        for(Long id : discentesId) {
            Usuario u = (Usuario) usuarioRepo.findById(id).orElseThrow(() -> new IllegalStateException("Usuario Invalido"));
            if (u instanceof Aluno a) discentes.add(a);
            else throw new IllegalStateException("Id de Aluno Invalido");
        }
        
        for(Long id : doscentesId) {
            Usuario u = (Usuario) usuarioRepo.findById(id).orElseThrow(() -> new IllegalStateException("Usuario Invalido"));
            if (u instanceof Professor p) doscentes.add(p);
            else throw new IllegalStateException("Id de Professor Invalido");
        }
        
        Turma turma = new Turma(nome, anoLetivo, true, disciplina, curso, discentes, doscentes);
        
        return registrarTurma(turma);
        
    }
    
    public Turma registrarTurma(String nome, int anoLetivo, Long disciplinaId, Long cursoId) {
        
        Disciplina disciplina = disciplinaRepo.findById(disciplinaId).orElseThrow(() -> new IllegalStateException("Disciplina Invalida"));
        Curso curso = cursoRepo.findById(cursoId).orElseThrow(() -> new IllegalStateException("Curso Invalido"));
        
        Turma turma = new Turma(nome, anoLetivo, true, disciplina, curso);
        
        return registrarTurma(turma);
        
    }
    
    public Turma atualizarTurma(Long turmaId, String turmaNome, List<Long> discentesId, List<Long> doscentesId, boolean ativo) {
        
        Turma turmaExistente = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        turmaExistente.setNome(turmaNome);
        turmaExistente.setAtivo(ativo);
        turmaExistente.getDiscentes().clear();
        turmaExistente.getDoscentes().clear();

        turmaRepo.save(turmaExistente);
        
        inserirAlunos(turmaExistente, discentesId);

        for (Long doscenteId : doscentesId)
            inserirProfessor(turmaExistente, doscenteId);

        return turmaExistente;
        
    }
    
    public Turma inserirAlunos(Turma turma, List<Long> discentesId) {
        
        List<Aluno> discentes = turma.getDiscentes();
        
        for(Long id : discentesId) {
            Usuario u = (Usuario) usuarioRepo.findById(id).orElseThrow(() -> new IllegalStateException("Usuario Invalido"));
            if (u instanceof Aluno a)
                if (!discentes.contains(a))
                    discentes.add(a);
            else throw new IllegalStateException("Id de Aluno Invalido");
        }
        
        turma.setDiscentes(discentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma inserirAlunos(Long turmaId, List<Long> discentesId) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return inserirAlunos(turma, discentesId);
        
    }
    
    
    
    public Turma inserirProfessor(Turma turma, Long doscenteId) {
        
        List<Professor> doscentes = turma.getDoscentes();
        
        Usuario u = (Usuario) usuarioRepo.findById(doscenteId).orElseThrow(() -> new IllegalStateException("Usuario Invalido"));
        if (u instanceof Professor p)
            if (!doscentes.contains(p))
                doscentes.add(p);
        else throw new IllegalStateException("Id de Professor Invalido");
        
        turma.setDoscentes(doscentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma inserirProfessor(Long turmaId, Long doscenteId) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return inserirProfessor(turma, doscenteId);
        
    }
    
    
    
    public Turma removerAluno(Turma turma, Long discenteId) {
        
        List<Aluno> discentes = turma.getDiscentes();
        
        Usuario u = (Usuario) usuarioRepo.findById(discenteId).orElseThrow(() -> new IllegalStateException("Usuario Invalido"));
        if (u instanceof Aluno a)
            if (!discentes.contains(a))
                discentes.remove(a);
        else throw new IllegalStateException("Id de Aluno Invalido");
        
        turma.setDiscentes(discentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma removerAluno(Long turmaId, Long discenteId) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return removerAluno(turma, discenteId);
        
    }
    
    
    
    public Turma removerProfessor(Turma turma, Long doscenteId) {
        
        List<Professor> doscentes = turma.getDoscentes();
        
        Usuario u = (Usuario) usuarioRepo.findById(doscenteId).orElseThrow(() -> new IllegalStateException("Usuario Invalido"));
        if (u instanceof Professor p)
            if (!doscentes.contains(p))
                doscentes.remove(p);
        else throw new IllegalStateException("Id de Professor Invalido");
        
        turma.setDoscentes(doscentes);
        
        turmaRepo.save(turma);
        
        return turma;
        
    }
    
    public Turma removerProfessor(Long turmaId, Long doscenteId) {
        
        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));
        
        return removerProfessor(turma, doscenteId);
        
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

    public List<Turma> listarTurmas() {

        List<Turma> turmas = turmaRepo.findAll();

        return turmas;

    }

    public Turma obterTurmaPorId(Long turmaId) {

        Turma turma = turmaRepo.findById(turmaId).orElseThrow(() -> new IllegalStateException("Turma Invalida"));

        return turma;

    }
    
    public List<Turma> listarTurmasPorCurso(Long cursoId) {

        Curso curso = cursoRepo.findById(cursoId).orElseThrow(() -> new IllegalStateException("Curso Invalido"));
        List<Turma> turmas = turmaRepo.findByCurso(curso);
        
        return turmas;
    }

    public List<Turma> listarTurmasPorDepartamento(Long departamentoId) {
        List<Turma> turmas = new ArrayList<>();
        List<Curso> cursos = cursoRepo.findByDepartamentoId(departamentoId);

        for (Curso curso : cursos) {
            turmas.addAll(turmaRepo.findByCurso(curso));
        }

        return turmas;
    }

    public List<Turma> buscarTurmaPorNome(String nome, HttpServletRequest request) {
        
        List<Turma> turmas = turmaRepo.findByNomeContainingIgnoreCase(nome);

        return turmas;
        
    }
    
}