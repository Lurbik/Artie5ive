package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    boolean existsByNome(String nome);
}