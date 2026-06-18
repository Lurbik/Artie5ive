package it.uniroma3.siw.Artie5ive.repository;

import it.uniroma3.siw.Artie5ive.model.Recensione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {

    @Query("SELECT r FROM Recensione r JOIN FETCH r.utente JOIN FETCH r.vino WHERE r.vino.id = :vinoId")
    List<Recensione> findByVinoId(@Param("vinoId") Long vinoId);

    @Query("SELECT r FROM Recensione r JOIN FETCH r.utente JOIN FETCH r.vino WHERE r.utente.id = :utenteId")
    List<Recensione> findByUtenteId(@Param("utenteId") Long utenteId);

    @Query("SELECT r FROM Recensione r JOIN FETCH r.utente JOIN FETCH r.vino")
    List<Recensione> findAllWithDetails();

    Optional<Recensione> findByUtenteIdAndVinoId(Long utenteId, Long vinoId);

    boolean existsByUtenteIdAndVinoId(Long utenteId, Long vinoId);
}