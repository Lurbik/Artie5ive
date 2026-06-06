package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Produttore;
import org.springframework.data.repository.CrudRepository;

public interface ProduttoreRepository extends CrudRepository<Produttore, Long> {
    boolean existsByNomeAndAnnoFondazione(String nome, Integer annoFondazione);
}