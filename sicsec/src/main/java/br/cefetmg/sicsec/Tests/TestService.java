/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Tests;

import br.cefetmg.sicsec.Model.Usuario.DadosBancarios;
import br.cefetmg.sicsec.Model.Usuario.Matricula;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.CPF;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import br.cefetmg.sicsec.Repository.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author davig
 */

@Service
public class TestService {
    
    @Autowired
    public DadosBancariosRepo dadosRepo;
    
    @Autowired
    public MatriculaRepo matriculaRepo;
    
    @Autowired
    public UsuarioRepo usuarioRepo;
    
    public Map<String, Object> login(String login, String password) {
        
        Usuario usuario;
        
        login = login.replaceAll("\\D", "");
        
        usuario = usuarioRepo.findByCpf(Long.parseLong(login)).get(0);
        
        if (usuario.getSenha().equals(password)) {
            
            Map<String, Object> result = new HashMap<>();
            result.put("user", usuario);
            result.put("matricula", usuario.getMatricula());
            result.put("dados bancarios", usuario.getDadosBancarios());
            return result;
        }
        return null;
        
    }
    
    public List<Usuario> criaBD() {
        
        List<UsuarioRaw> usuariosNovos = new ArrayList<>();
        
        usuariosNovos.add(new UsuarioRaw(0L, Cargo.ALUNO, "senha", 0L, 12345678900L, "José", "jose@gmail.com", "+31 0000-0000", 202512345L, 0L, 99876543211L, "Santander", "1234", "00001111"));
        usuariosNovos.add(new UsuarioRaw(1L, Cargo.PROFESSOR, "senha123", 1L, 98765432100L, "Maria Silva", "maria.silva@gmail.com", "+31 91234-5678", 202512346L, 1L, 11223344556L, "Banco do Brasil", "4321", "00002222"));
        usuariosNovos.add(new UsuarioRaw(2L, Cargo.PESQUISADOR, "abc123", 2L, 12312312399L, "Carlos Pereira", "carlos.pereira@cefetmg.br", "+31 99876-5432", 202512347L, 2L, 99887766554L, "Caixa", "5678", "00003333"));
        usuariosNovos.add(new UsuarioRaw(3L, Cargo.ALUNO, "senhaSegura", 3L, 32165498700L, "Ana Souza", "ana.souza@hotmail.com", "+31 91122-3344", 202512348L, 3L, 88776655443L, "Itaú", "8765", "00004444"));
        usuariosNovos.add(new UsuarioRaw(4L, Cargo.BIBLIOTECARIO, "livros@123", 4L, 65498732100L, "Roberto Lima", "roberto.lima@biblioteca.com", "+31 97777-8888", 202512349L, 4L, 77665544332L, "Bradesco", "1111", "00005555"));
        usuariosNovos.add(new UsuarioRaw(5L, Cargo.PROFESSOR, "prof#2025", 5L, 11122233344L, "Fernanda Alves", "fernanda.alves@cefetmg.br", "+31 90000-1122", 202512350L, 5L, 66554433221L, "Santander", "2222", "00006666"));
        usuariosNovos.add(new UsuarioRaw(6L, Cargo.PESQUISADOR, "pesq!456", 6L, 99988877766L, "Gustavo Torres", "gustavo.torres@lab.com", "+31 95555-6666", 202512351L, 6L, 55443322110L, "Nubank", "3333", "00007777"));
        usuariosNovos.add(new UsuarioRaw(7L, Cargo.ALUNO, "aluno@2025", 7L, 44455566677L, "Laura Mendes", "laura.mendes@gmail.com", "+31 96666-7777", 202512352L, 7L, 44332211009L, "Inter", "4444", "00008888"));
        usuariosNovos.add(new UsuarioRaw(8L, Cargo.BIBLIOTECARIO, "bib#senha", 8L, 88899900011L, "Paulo Rocha", "paulo.rocha@cefetmg.br", "+31 92222-3333", 202512353L, 8L, 33221100998L, "Banco do Brasil", "5555", "00009999"));
        usuariosNovos.add(new UsuarioRaw(9L, Cargo.PROFESSOR, "ensina123", 9L, 55566677788L, "Beatriz Faria", "beatriz.faria@prof.com", "+31 98888-9999", 202512354L, 9L, 22110099887L, "Caixa", "6666", "00001010"));
        usuariosNovos.add(new UsuarioRaw(10L, Cargo.ALUNO, "123aluno!", 10L, 77788899900L, "Lucas Oliveira", "lucas.oliveira@gmail.com", "+31 93333-4444", 202512355L, 10L, 11009988776L, "Itaú", "7777", "00001111"));
        usuariosNovos.add(new UsuarioRaw(11L, Cargo.ADMINISTRADOR, "root", 11L, 11111111111L, "root", "admin@cefetmg.br", "+31 1", 111111111L, 11L, 11111111111L, "todos", "1111", "11111111"));
        
        for(UsuarioRaw userRaw : usuariosNovos) {
            
            Usuario user = userRaw.toEntity();
            
            dadosRepo.save(user.getDadosBancarios());
            matriculaRepo.save(user.getMatricula());
            usuarioRepo.save(user);
            
        }
        
        return usuarioRepo.findAll();
        
    }
    
    public void insereUsuario() {
        
    }
    
}

class UsuarioRaw {
    
    public Cargo cargo;
    public String senha;
    public Long cpfMatricula;
    public String nome;
    public String email;
    public String telefone;
    public Long numeroMatricula;
    public Long cpfDados;
    public String banco;
    public String agencia;
    public String conta;

    public UsuarioRaw(Long id, Cargo cargo, String senha, Long idMatricula, 
            Long cpfMatricula, String nome, String email, String telefone, 
            Long numeroMatricula, Long idDados, Long cpfDados, String banco, 
            String agencia, String conta) {
        this.cargo = cargo;
        this.senha = senha;
        this.cpfMatricula = cpfMatricula;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.numeroMatricula = numeroMatricula;
        this.cpfDados = cpfDados;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
    }
    
    public Usuario toEntity() {
        
        Usuario user = new Usuario();
        
        user.setCargo(cargo);
        user.setSenha(senha);
        
        Matricula matricula = new Matricula();
        
        CPF _cpfMatricula = new CPF();
        _cpfMatricula.setCpf(cpfMatricula);
        
        matricula.setCpf(_cpfMatricula);
        
        matricula.setNome(nome);
        matricula.setEmail(email);
        matricula.setTelefone(telefone);
        matricula.setNumeroMatricula(numeroMatricula);
        
        DadosBancarios dados = new DadosBancarios();
        
        CPF _cpfDados = new CPF();
        _cpfDados.setCpf(cpfDados);
        
        dados.setCpf(_cpfDados);
        
        dados.setBanco(banco);
        dados.setAgencia(agencia);
        dados.setConta(conta);
        
        user.setMatricula(matricula);
        user.setDadosBancarios(dados);
        
        return user;
    }
    
}