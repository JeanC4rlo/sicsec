package br.cefetmg.sicsec.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.cefetmg.sicsec.Model.Usuario.Documento;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Administrador;
import br.cefetmg.sicsec.Model.Usuario.Administrador.ChefeDepartamento;
import br.cefetmg.sicsec.Model.Usuario.Administrador.Coordenador;
import br.cefetmg.sicsec.Model.Util.Enum.Cargo;
import br.cefetmg.sicsec.Model.Util.Enum.StatusDocumento;
import br.cefetmg.sicsec.Repository.DocumentoRepository;
import jakarta.transaction.Transactional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepo;

    @Autowired
    private AssinaturaService assinaturaService;

    @Autowired
    private UsuarioService usuarioService;

    public Documento atualizarDocumento(Long id, String titulo, Long dataExpiracaoMs, boolean arquivado) {
        Documento documento = documentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado com ID: " + id));

        documento.setTitulo(titulo);
        documento.setDataExpiracao(dataExpiracaoMs != null ? new Date(dataExpiracaoMs) : null);

        if (arquivado && documento.getStatus() != StatusDocumento.ASSINADO) {
            documento.setStatus(StatusDocumento.ARQUIVADO);
        } else if (!arquivado && documento.getStatus() == StatusDocumento.ARQUIVADO) {
            documento.setStatus(StatusDocumento.PENDENTE);
        }

        return documentoRepo.save(documento);
    }

    public List<Documento> listarDocumentosComFiltros(Usuario usuario, String busca, String status) {
        if ("TODOS".equals(status)) {
            return buscarPorTituloECriador(usuario, busca);
        }

        try {
            StatusDocumento statusDoc = StatusDocumento.valueOf(status.toUpperCase());
            return buscarPorTituloStatusECriador(usuario, busca, statusDoc);
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    private List<Documento> buscarPorTituloECriador(Usuario usuario, String busca) {
        if (busca != null && !busca.trim().isEmpty()) {
            return documentoRepo.findByCreatorAndTitle(usuario, busca);
        }
        return documentoRepo.findByCreator(usuario);
    }

    private List<Documento> buscarPorTituloStatusECriador(Usuario usuario, String busca, StatusDocumento status) {
        if (busca != null && !busca.trim().isEmpty()) {
            return documentoRepo.findByCreatorStatusAndTitle(usuario, status, busca);
        }
        return documentoRepo.findByCreatorAndStatus(usuario, status);
    }

    public List<Documento> getDocumentosPorStatus(Usuario usuario, StatusDocumento status) {
        if (usuario.getCargo() == Cargo.ADMINISTRADOR) {
            Administrador administrador = (Administrador) usuario;
            switch (administrador.getCargoAdministrador()) {
                case CHEFE_DE_DEPARTAMENTO:
                    ChefeDepartamento chefe = (ChefeDepartamento) administrador;
                    if (chefe.getDepartamento() != null) {
                        return documentoRepo.findByStatusAndDepartment(status, chefe.getDepartamento());
                    }
                    break;
                case COORDENADOR:
                    Coordenador coordenador = (Coordenador) administrador;
                    if (coordenador.getCurso() != null) {
                        return documentoRepo.findByStatusAndCourse(status, coordenador.getCurso());
                    }
                    break;
                case ROOT:
                    return documentoRepo.findByStatus(status);
            }
        }
        return List.of();
    }

    @Transactional
    public Documento criarDocumento(String titulo, String conteudo, Usuario criador, Date dataExpiracao,
            List<Long> usuariosAssinantesIds) {
        Documento documento = new Documento();
        documento.setTitulo(titulo);
        documento.setConteudo(conteudo);
        documento.setCriador(criador);

        documento.setStatus(StatusDocumento.PENDENTE);

        if (usuariosAssinantesIds == null || usuariosAssinantesIds.isEmpty()) {
            documento.setStatus(StatusDocumento.ARQUIVADO);
        }

        documento.setDataCriacao(new Date());
        documento.setDataExpiracao(dataExpiracao);

        Documento documentoSalvo = documentoRepo.save(documento);

        if (usuariosAssinantesIds != null && !usuariosAssinantesIds.isEmpty()) {
            List<Usuario> usuariosAssinantes = usuarioService.findAllById(usuariosAssinantesIds);
            assinaturaService.criarAssinaturas(documentoSalvo, usuariosAssinantes);
        }

        return documentoSalvo;
    }

    @Transactional
    public Documento criarDocumento(String titulo, String conteudo, Usuario criador, Date dataExpiracao) {
        return criarDocumento(titulo, conteudo, criador, dataExpiracao, null);
    }

    public List<Documento> getDocumentos(Usuario usuario) {
        return documentoRepo.findByCreator(usuario);
    }

    public Optional<Documento> findByIdAndUsuario(Long id, Usuario usuario) {
        return documentoRepo.findByIdAndCreator(id, usuario);
    }

    public void salvar(Documento documento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvar'");
    }
}