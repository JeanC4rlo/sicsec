package br.cefetmg.sicsec.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.cefetmg.sicsec.Model.Curso.Curso;
import br.cefetmg.sicsec.Model.Curso.Departamento;
import br.cefetmg.sicsec.Model.Usuario.Documento;
import br.cefetmg.sicsec.Model.Usuario.Usuario;
import br.cefetmg.sicsec.Model.Util.Enum.StatusDocumento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    
    @Query("SELECT d FROM Documento d WHERE d.id = :id AND d.criador = :criador")
    Optional<Documento> findByIdAndCreator(@Param("id") Long id, @Param("criador") Usuario criador);
    
    @Query("SELECT d FROM Documento d WHERE d.criador = :criador ORDER BY d.dataCriacao DESC")
    List<Documento> findByCreator(@Param("criador") Usuario criador);
    
    @Query("SELECT d FROM Documento d WHERE d.criador = :criador AND LOWER(d.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) ORDER BY d.dataCriacao DESC")
    List<Documento> findByCreatorAndTitle(@Param("criador") Usuario criador, @Param("titulo") String titulo);
    
    @Query("SELECT d FROM Documento d WHERE d.criador = :criador AND d.status = :status ORDER BY d.dataCriacao DESC")
    List<Documento> findByCreatorAndStatus(@Param("criador") Usuario criador, @Param("status") StatusDocumento status);
    
    @Query("SELECT d FROM Documento d WHERE d.criador = :criador AND d.status = :status AND LOWER(d.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')) ORDER BY d.dataCriacao DESC")
    List<Documento> findByCreatorStatusAndTitle(@Param("criador") Usuario criador, @Param("status") StatusDocumento status, @Param("titulo") String titulo);
    
    @Query("SELECT d FROM Documento d WHERE d.status = :status ORDER BY d.dataCriacao DESC")
    List<Documento> findByStatus(@Param("status") StatusDocumento status);
    
    /*
    @Query("SELECT d FROM Documento d WHERE d.status = :status AND d.criador.departamento = :departamento ORDER BY d.dataCriacao DESC")
    List<Documento> findByStatusAndDepartment(@Param("status") StatusDocumento status, @Param("departamento") Departamento departamento);
    
    @Query("SELECT d FROM Documento d WHERE d.status = :status AND d.criador.curso = :curso ORDER BY d.dataCriacao DESC")
    List<Documento> findByStatusAndCourse(@Param("status") StatusDocumento status, @Param("curso") Curso curso);
    */


}