package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Vino;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface VinoRepository extends CrudRepository<Vino, Long> {
    List<Vino> findByAnnata(Integer annata);

    List<Vino> findByProduttoreId(Long produttoreId);

    List<Vino> findByRegioneId(Long regioneId);

    boolean existsByNomeAndAnnataAndProduttoreId(String nome, Integer annata, Long produttoreId);
}