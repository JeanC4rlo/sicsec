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
import br.cefetmg.sicsec.Model.Curso.Turma.Cronograma;
import br.cefetmg.sicsec.Model.Curso.Turma.Noticia;
import br.cefetmg.sicsec.Model.Curso.Turma.Turma;
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

            List<Turma> turmas = tService.listarTurmasDeAluno(session);
         
            List<Object> turmasMap = new ArrayList<>();

            for(Turma turma : turmas) {

                if (turma == null) continue;
                if (!turma.isAtivo()) continue;

                turmasMap.add(Map.of(
                    "id", turma.getId(),
                    "nome", turma.getNome(),
                    "tipo", turma.getTipo()
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
                "disciplina", turma.getDisciplina().getNome(),
                "curso", turma.getCurso().getNome()
        ));
        
        List<Aula> aulas = turma.getAulas();

        List<Map<String, Object>> aulasMap = new ArrayList<>();

        for (Aula aula : aulas) {

            aulasMap.add(Map.of(
                "diaSemana", aula.getDia().toString(),
                "horarioInicio", aula.getHorario().getInicio().toString(),
                "horarioFim", aula.getHorario().getFim().toString(),
                "sala", aula.getSala()
            ));

        }

        if (aulasMap.isEmpty()) {
            turmaMap.put("aulas", false);
        }
        else turmaMap.put("aulas", aulasMap);
        
        List<Noticia> noticias = turma.getNoticias();

        if (noticias.isEmpty()) {
            turmaMap.put("ultimaNoticia", false);
        } else {
            noticias.sort((n1, n2) -> n1.getData().compareTo(n2.getData()));
            Noticia ultimaNoticia = noticias.get(noticias.size() - 1);
            turmaMap.put("ultimaNoticia", Map.of(
                "autor", ultimaNoticia.getAutor().getMatricula().getNome(),
                "data", ultimaNoticia.getData().toString(),
                "manchete", ultimaNoticia.getManchete(),
                "corpo", ultimaNoticia.getCorpo()
            ));
        }

        List<Cronograma> cronogramas = turma.getCronogramas();

        if (cronogramas.isEmpty()) {
            turmaMap.put("proximoCronograma", false);
        } else {

            Cronograma proximoCronograma = null;

            for (Cronograma cronograma : cronogramas) {
                if (proximoCronograma == null || 
                        (cronograma.getData().after(new java.util.Date()) && 
                            cronograma.getData().before(proximoCronograma.getData())) ||
                        (proximoCronograma.getData().before(new java.util.Date()) &&
                            cronograma.getData().after(proximoCronograma.getData())))
                    proximoCronograma = cronograma;
                
            }

            if (proximoCronograma == null) {
                turmaMap.put("proximoCronograma", false);
            } else {
                turmaMap.put("proximoCronograma", Map.of(
                    "autor", proximoCronograma.getAutor().getMatricula().getNome(),
                    "data", proximoCronograma.getData().toString(),
                    "titulo", proximoCronograma.getTitulo(),
                    "descricao", proximoCronograma.getDescricao(),
                    "arquivo", proximoCronograma.getArquivo(),
                    "tipo", proximoCronograma.getTipo().toString()
                ));
            }

        }

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
                "autor", noticia.getAutor().getMatricula().getNome(),
                "data", noticia.getData().toString(),
                "manchete", noticia.getManchete(),
                "corpo", noticia.getCorpo()
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
                "autor", cronograma.getAutor().getMatricula().getNome(),
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
