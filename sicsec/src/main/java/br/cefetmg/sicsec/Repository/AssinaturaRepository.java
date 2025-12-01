package br.cefetmg.sicsec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.cefetmg.sicsec.Model.Usuario.Assinatura;
import br.cefetmg.sicsec.Model.Usuario.Documento;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.StatusAssinatura;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    @Query("SELECT a FROM Assinatura a WHERE a.id = :id AND a.usuario = :usuario")
    Optional<Assinatura> findByIdAndUser(@Param("id") Long id, @Param("usuario") Usuario usuario);

    @Query("SELECT a FROM Assinatura a WHERE a.documento = :documento ORDER BY a.dataCriacao DESC")
    List<Assinatura> findByDocument(@Param("documento") Documento documento);

    @Query("SELECT a FROM Assinatura a WHERE a.usuario = :usuario ORDER BY a.dataCriacao DESC")
    List<Assinatura> findByUser(@Param("usuario") Usuario usuario);

    @Query("SELECT a FROM Assinatura a WHERE a.usuario = :usuario AND a.status = :status ORDER BY a.dataCriacao DESC")
    List<Assinatura> findByUserAndStatus(@Param("usuario") Usuario usuario, @Param("status") StatusAssinatura status);

    @Query("SELECT a FROM Assinatura a WHERE a.documento = :documento AND a.usuario = :usuario")
    Optional<Assinatura> findByDocumentAndUser(@Param("documento") Documento documento, @Param("usuario") Usuario usuario);
}