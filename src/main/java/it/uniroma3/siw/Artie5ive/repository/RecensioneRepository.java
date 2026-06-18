package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    List<Recensione> findByVinoId(Long vinoId);
    List<Recensione> findByUtenteId(Long utenteId);
    Optional<Recensione> findByUtenteIdAndVinoId(Long utenteId, Long vinoId);
    boolean existsByUtenteIdAndVinoId(Long utenteId, Long vinoId);
    
}