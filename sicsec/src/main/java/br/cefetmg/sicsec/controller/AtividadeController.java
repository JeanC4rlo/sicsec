package br.cefetmg.sicsec.controller;

import java.util.List;

import br.cefetmg.sicsec.dto.HomeAtividadesDTO;
import br.cefetmg.sicsec.model.Atividade;
import br.cefetmg.sicsec.repository.AtividadeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@CrossOrigin(origins = "*")


public class AtividadeController {
    @Autowired
    private AtividadeRepository atividadesRepository;

    @PostMapping("/salvar")
    public ResponseEntity<Atividade> salvar(@RequestBody Atividade atividade) {
        Atividade nova = atividadesRepository.save(atividade);
        return ResponseEntity.ok(nova);
    }

    @GetMapping("/atividades")
    public List<Atividade> Listar() {
        return atividadesRepository.findAll();
    }
    
    @GetMapping("/home-atividades")
    public List<HomeAtividadesDTO> ListarHomeAtividades() {
        return atividadesRepository.findAll().stream()
        .map(a -> new HomeAtividadesDTO(a.getNome(), a.getTipo(), a.getValor(), a.getDataEncerramento(), a.getHoraEncerramento()))
        .toList();
    }
}
