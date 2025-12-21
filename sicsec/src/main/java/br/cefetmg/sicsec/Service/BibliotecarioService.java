package br.cefetmg.sicsec.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Bibliotecario.Bibliotecario;
import br.cefetmg.sicsec.Repository.Usuarios.BibliotecarioRepository;

@Service
public class BibliotecarioService {
    @Autowired
    BibliotecarioRepository bibliotecarioRepository;
    
    public Optional<Bibliotecario> findById(Long id) {
        return bibliotecarioRepository.findById(id);
    }
}
