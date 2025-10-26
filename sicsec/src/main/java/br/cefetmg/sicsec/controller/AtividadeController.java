package br.cefetmg.sicsec.controller;

import br.cefetmg.sicsec.model.Atividade;
import br.cefetmg.sicsec.repository.AtividadeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/salvar")
@CrossOrigin(origins = "*")


public class AtividadeController {
    @Autowired
    private AtividadeRepository atividadesRepository;

    @PostMapping
    public ResponseEntity<Atividade> salvar(@RequestBody Atividade atividade) {
        Atividade nova = atividadesRepository.save(atividade);
        return ResponseEntity.ok(nova);
    }
    
}
