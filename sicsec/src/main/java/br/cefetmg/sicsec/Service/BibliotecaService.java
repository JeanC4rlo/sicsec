package br.cefetmg.sicsec.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Biblioteca.Biblioteca;
import br.cefetmg.sicsec.Repository.BibliotecaRepository;

@Service
public class BibliotecaService {
    @Autowired
    BibliotecaRepository bibliotecaRepository;

    public List<Biblioteca> findAllById(List<Long> ids) {
        return bibliotecaRepository.findAllById(ids);
    }
}
