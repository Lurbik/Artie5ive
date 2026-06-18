    package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<Utente> findByEmail(String email);
    boolean existsByEmail(String email);
}