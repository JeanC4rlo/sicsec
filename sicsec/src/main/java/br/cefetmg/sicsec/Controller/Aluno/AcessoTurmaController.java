package br.cefetmg.sicsec.Controller.Aluno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.cefetmg.sicsec.Model.Curso.Aula;
import br.cefetmg.sicsec.Model.Curso.Turma.Noticia;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
import br.cefetmg.sicsec.Model.Usuario.Aluno.Aluno;
import br.cefetmg.sicsec.Model.Usuario.Professor.Professor;
import br.cefetmg.sicsec.Service.TurmaService;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author davig
 */
@Controller
@RequestMapping("/api/aluno/acesso/turma")
public class AcessoTurmaController {
    
    
    @Autowired
    private TurmaService tService;

    @PostMapping("/listar")
    public ResponseEntity<List<Object>> listarTurmasDeAluno(HttpSession session) {

        try {

            System.out.println("Listando turmas de aluno...");

            List<Turma> turmas = tService.listarTurmasDeAluno(session);
         
            List<Object> turmasMap = new ArrayList<>();

            for(Turma turma : turmas) {

                if (turma == null) continue;
                if (!turma.isAtivo()) continue;

                turmasMap.add(Map.of(
                    "id", turma.getId(),
                    "nome", turma.getNome()
                ));

            }

            return ResponseEntity.ok(turmasMap);

        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(null);
        }

    }  

    @PostMapping("info/{id}")
    public ResponseEntity<Map<String, Object>> getTurmaInfo(@PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);

        if (turma == null) {
            return ResponseEntity.status(404).body(null);
        }

        Map<String, Object> turmaMap = new HashMap<>();
        turmaMap.putAll(Map.of(
                "nome", turma.getNome(),
                "disciplina", turma.getDisciplina().getNome()
        ));
        
        List<Aula> aulas = turma.getAulas();

        List<Map<String, Object>> aulasMap = new ArrayList<>();

        for (Aula aula : aulas) {

            aulasMap.add(Map.of(
                "dia", aula.getDia().toString(),
                "horaInicio", aula.getHorario().getInicio().toString(),
                "horaFim", aula.getHorario().getFim().toString(),
                "sala", aula.getSala()
            ));

        }

        if (aulasMap.isEmpty()) {
            aulasMap.add(Map.of(
                "dia", "Á definir",
                "horaInicio", "Á definir",
                "horaFim", "Á definir",
                "sala", "Á definir"
            ));
        }

        System.out.println(aulasMap);

        turmaMap.put("aulas", aulasMap);

        return ResponseEntity.ok(turmaMap);

    }
    
    @PostMapping("/noticias/{id}")
    public ResponseEntity<List<Map<String, Object>>> getNoticiasTurma(@PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);
        
        if (turma == null) {
            return ResponseEntity.badRequest().body(null); 
        }
        
        List<Map<String, Object>> noticiasMap = new ArrayList<>();

        for (Noticia noticia : turma.getNoticias()) {
            noticiasMap.add(Map.of(
                "Autor", noticia.getAutor().getMatricula().getNome(),
                "data", noticia.getData().toString(),
                "Manchete", noticia.getManchete(),
                "Corpo", noticia.getCorpo()
            ));
        }

        return ResponseEntity.ok(noticiasMap);

    }

    @PostMapping("/atividades/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAtividadesTurma(@PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);
        
        if (turma == null) {
            return ResponseEntity.badRequest().body(null); 
        }
        
        List<Map<String, Object>> atividadesMap = new ArrayList<>();

        // TO DO: Implementar atividades quando o modelo estiver pronto

        return ResponseEntity.ok(null);

    }

    @PostMapping("/cronogramas/{id}")
    public ResponseEntity<List<Map<String, Object>>> getCronogramasTurma(@PathVariable Long id) {
        
        Turma turma = tService.obterTurmaPorId(id);
        
        if (turma == null) {
            return ResponseEntity.badRequest().body(null); 
        }
        
        List<Map<String, Object>> cronogramasMap = new ArrayList<>();

        for (var cronograma : turma.getCronogramas()) {
            cronogramasMap.add(Map.of(
                "Autor", cronograma.getAutor().getMatricula().getNome(),
                "data", cronograma.getData().toString(),
                "titulo", cronograma.getTitulo(),
                "descricao", cronograma.getDescricao(),
                "arquivo", cronograma.getArquivo(),
                "tipo", cronograma.getTipo().toString()
            ));
        }

        return ResponseEntity.ok(cronogramasMap);

    }

}
