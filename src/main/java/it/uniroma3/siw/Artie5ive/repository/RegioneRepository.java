package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Regione;
import org.springframework.data.repository.CrudRepository;

public interface RegioneRepository extends CrudRepository<Regione, Long> {
    boolean existsByNome(String nome);
}