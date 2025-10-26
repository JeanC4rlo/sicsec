/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.sicsec.Model.Usuario;

import br.cefetmg.sicsec.Model.DocumentoModel;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import java.util.List;

/**
 *
 * @author davig
 */
public class UsuarioModel {
    
    private Cargo cargo;
    private MatriculaModel matricula;
    private String senha;
    private List<DocumentoModel> documentos;

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    public MatriculaModel getMatricula() {
        return matricula;
    }

    public void setMatricula(MatriculaModel matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<DocumentoModel> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoModel> documentos) {
        this.documentos = documentos;
    }
    
    
    
}
