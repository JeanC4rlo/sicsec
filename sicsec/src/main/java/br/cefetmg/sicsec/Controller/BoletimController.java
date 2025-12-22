package br.cefetmg.sicsec.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.cefetmg.sicsec.Model.Usuario.Aluno.*;
import br.cefetmg.sicsec.Service.BoletimService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/boletim")
public class BoletimController {
    
    @Autowired
    private BoletimService bService;

    @PostMapping("/aluno/acesso")
    public ResponseEntity<Object> acessoBoletim(HttpSession session) {
        
        List<Boletim> boletins = null;

        try {
            boletins = bService.acessarBoletim(session);
        }
        catch (IllegalAccessException e) {
            return new ResponseEntity<>(
                java.util.Map.of("erro", e.getMessage()),
                HttpStatus.FORBIDDEN
            );
        }

        if (boletins == null || boletins.isEmpty())
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
                );
        
        List<Map<String, Object>> boletinsMap = new ArrayList<>();

        for(Boletim boletim : boletins) {

            Map<String, Object> boletimMap = new HashMap<String, Object>();
            boletimMap.put("situacaoFinal", boletim.getSituacaoDoAno());

            List<Map<String, Object>> componentesMaps = new ArrayList<Map<String, Object>>();
            for(ComponenteCurricular componente : boletim.getComponentes()) {

                Map<String, Object> componenteMap = new HashMap<String, Object>();
                componenteMap.put("faltas", componente.getFaltas());
                componenteMap.put("disciplina", componente.getDisciplina());
                componenteMap.put("situacao", componente.getSituacao());
                componenteMap.put("notaFinal", componente.getNotaFinal());

                List<Map<String, Object>> notasMap = new ArrayList<Map<String, Object>>();
                for(Nota nota : componente.getNotas()) {
                    Map<String, Object> notaMap = new HashMap<String, Object>();
                    notaMap.put("avaliacao", nota.getAvaliacao());
                    notaMap.put("bimestre", nota.getBimestre());
                    notaMap.put("valor", nota.getValor());
                    notasMap.add(notaMap);
                }
                componenteMap.put("notas", notasMap);
                boletinsMap.add(componenteMap);

            }
            boletimMap.put("componentes", componentesMaps);

        }

        return ResponseEntity.ok(boletinsMap);

    }
    
}