/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Repository;

import br.cefetmg.sicsec.Model.Usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author davig
 */
@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

     @Query("""
            SELECT u
            FROM Usuario u
            JOIN u.matricula m
            WHERE m.cpf.cpf = :cpf
            """)
    Usuario findByCpf(@Param("cpf") Long cpf);
    
}