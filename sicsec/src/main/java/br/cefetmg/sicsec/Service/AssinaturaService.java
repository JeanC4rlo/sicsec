package br.cefetmg.sicsec.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.cefetmg.sicsec.Model.Usuario.Assinatura;
import br.cefetmg.sicsec.Model.Usuario.Documento;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.StatusAssinatura;
import br.cefetmg.sicsec.Model.Util.Enum.StatusDocumento;
import br.cefetmg.sicsec.Repository.AssinaturaRepository;
import br.cefetmg.sicsec.Repository.DocumentoRepository;

import java.util.Date;
import java.util.List;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository assinaturaRepo;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Transactional
    public List<Assinatura> criarAssinaturas(Documento documento, List<Usuario> usuarios) {
        List<Assinatura> assinaturas = usuarios.stream()
                .map(usuario -> new Assinatura(documento, usuario))
                .toList();

        return assinaturaRepo.saveAll(assinaturas);
    }

    @Transactional
    public Assinatura atualizarAssinatura(Long assinaturaId, StatusAssinatura status, Usuario usuario) {
        Assinatura assinatura = assinaturaRepo.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        if (assinatura.getUsuario().getId() != usuario.getId()) {
            throw new RuntimeException("Usuário não autorizado a rejeitar esta assinatura");
        }

        if (!assinatura.isPendente()) {
            throw new RuntimeException("Esta assinatura já foi processada");
        }

        assinatura.setStatus(status);

        Documento documento = assinatura.getDocumento();

        List<Assinatura> assinaturas = assinatura.getDocumento().getAssinaturas();
        boolean todasAssinadas = true;

        for (Assinatura a : assinaturas) {
            if (a.isPendente()) {
                todasAssinadas = false;
                break;
            }
        }

        if (todasAssinadas) {
            documento.setStatus(StatusDocumento.ASSINADO);
            documentoRepository.save(documento);
        }

        assinatura.setDataAssinatura(new Date());

        return assinaturaRepo.save(assinatura);
    }

    public List<Assinatura> buscarAssinaturasPorDocumento(Documento documento) {
        return assinaturaRepo.findByDocument(documento);
    }

    public List<Assinatura> buscarAssinaturasPorUsuario(Usuario usuario) {
        return assinaturaRepo.findByUser(usuario);
    }

    public List<Assinatura> buscarAssinaturasPendentesPorUsuario(Usuario usuario) {
        return assinaturaRepo.findByUserAndStatus(usuario, StatusAssinatura.PENDENTE);
    }
}