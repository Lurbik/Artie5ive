package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Vino;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface VinoRepository extends CrudRepository<Vino, Long> {
    List<Vino> findByAnnata(Integer annata);

    List<Vino> findByProduttoreId(Long produttoreId);

    List<Vino> findByRegioneId(Long regioneId);

    boolean existsByNomeAndAnnataAndProduttoreId(String nome, Integer annata, Long produttoreId);

    List<Vino> findByAnnataAndRegioneId(Integer annata, Long regioneId);

    List<Vino> findByAnnataAndProduttoreId(Integer annata, Long produttoreId);

    List<Vino> findByRegioneIdAndProduttoreId(Long regioneId, Long produttoreId);

    List<Vino> findByAnnataAndRegioneIdAndProduttoreId(Integer annata, Long regioneId, Long produttoreId);

    @Query("SELECT v FROM Vino v LEFT JOIN FETCH v.categorie LEFT JOIN FETCH v.recensioni WHERE v.id = :id")
    Optional<Vino> findByIdWithDetails(@Param("id") Long id);
}