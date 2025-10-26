/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.cefetmg.sicsec.Tests;

import br.cefetmg.sicsec.Model.Usuario.DadosBancariosModel;
import br.cefetmg.sicsec.Model.Usuario.MatriculaModel;
import org.springframework.stereotype.Service;
import br.cefetmg.sicsec.Model.Usuario.UsuarioModel;
import br.cefetmg.sicsec.Model.Util.CPF;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import java.util.Map;

/**
 *
 * @author davig
 */
@Service
public class TestModelService {
    
    public UsuarioModel ReceberUsuario(Map<String, Object> body) {
        
        String cargo = (String) body.get("cargo");
        String senha = (String) body.get("senha");
        String cpfMatricula = (String) body.get("cpfMatricula");
        String nome = (String) body.get("nome");
        String email = (String) body.get("email");
        String telefone = (String) body.get("telefone");
        String numeroMatricula = (String) body.get("numeroMatricula");
        String banco = (String) body.get("banco");
        String agencia = (String) body.get("agencia");
        String conta = (String) body.get("conta");
        String cpfBanco = (String) body.get("cpfBanco");
        
        return CriarUsuario(cargo, senha, cpfMatricula, nome, email, telefone, 
                numeroMatricula, banco, agencia, conta, cpfBanco);
    }
    
    public UsuarioModel CriarUsuario(String cargo, String senha, String cpfMatricula, String nome, 
            String email, String telefone, String numeroMatricula, String banco, 
            String agencia, String conta, String cpfBanco) {
        
        UsuarioModel user = new UsuarioModel();
        
        user.setCargo(Cargo.valueOf(cargo));
        user.setSenha(senha);
        user.setDocumentos(null);
        
        MatriculaModel matricula = new MatriculaModel();
        
        CPF cpf = DestrincharCPF(cpfMatricula);
        matricula.setCpf(cpf);
        
        matricula.setNome(nome);
        matricula.setEmail(email);
        matricula.setTelefone(telefone);
        matricula.setNumeroMatricula(Long.valueOf(numeroMatricula));
        
        DadosBancariosModel dadosBancarios = new DadosBancariosModel();
        
        dadosBancarios.setBanco(banco);
        dadosBancarios.setAgencia(agencia);
        dadosBancarios.setConta(conta);
        
        cpf = DestrincharCPF(cpfBanco);
        dadosBancarios.setCpf(cpf);
        
        matricula.setDadosBancarios(dadosBancarios);
        
        user.setMatricula(matricula);
        
        return user;
        
    }
    
    public CPF DestrincharCPF(String cpfS) {
        
        CPF cpf = new CPF();
        Long cpfL = Long.valueOf(cpfS.replaceAll("\\D", ""));
        
        int digitosUnicos = Math.toIntExact(cpfL/1000);
        int sufixo = Math.toIntExact(cpfL%1000);
        
        byte regiaoFiscal = (byte) (sufixo/100);
        byte digitoVerificador1 = (byte) ((sufixo/10)%10);
        byte digitoVerificador2 = (byte) ((sufixo%100));
        
        cpf.setDigitosUnicos(digitosUnicos);
        cpf.setRegiaoFiscal(regiaoFiscal);
        cpf.setDigitoVerificador1(digitoVerificador1);
        cpf.setDigitoVerificador2(digitoVerificador2);
                
        return cpf;
    }
    
}
