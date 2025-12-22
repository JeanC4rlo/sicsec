package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Repository.DisciplinaRepo;
import br.cefetmg.sicsec.Repository.Usuarios.UsuarioRepo;
import br.cefetmg.sicsec.Model.Curso.Disciplina;
import br.cefetmg.sicsec.Dto.DisciplinaDTO;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {
    
    @Autowired
    private DisciplinaRepo disciplinaRepo;
    
    @Autowired
    private UsuarioRepo usuarioRepo;

    public List<Disciplina> getByCursoId(Long cursoId) {
        return disciplinaRepo.findByCursoId(cursoId);
    }
    
    public Optional<Disciplina> buscarPorCodigo(String codigo) {
        return disciplinaRepo.findByCodigo(codigo);
    }
    
    public void salvarComMatriculas(DisciplinaDTO dto) {
        Disciplina disciplina = disciplinaRepo.findByCodigo(dto.getCodigo())
                .orElse(new Disciplina());
        
        disciplina.setNome(dto.getNome());
        disciplina.setCodigo(dto.getCodigo());
        disciplina.setCargaHoraria(dto.getCargaHoraria());
        disciplina.setModalidade(dto.getModalidade());
        
        if(dto.getProfessorIds() != null && !dto.getProfessorIds().isEmpty()) {
            disciplina.setProfessores(usuarioRepo.findAllById(dto.getProfessorIds()));
        }
        
        if (dto.getAlunoIds() != null && !dto.getAlunoIds().isEmpty()) {
            disciplina.setAlunos(usuarioRepo.findAllById(dto.getAlunoIds()));
        }
        
        disciplinaRepo.save(disciplina);
    }
}