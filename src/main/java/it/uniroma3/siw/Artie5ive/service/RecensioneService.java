package it.uniroma3.siw.Artie5ive.service;

import it.uniroma3.siw.Artie5ive.model.Recensione;
import it.uniroma3.siw.Artie5ive.repository.RecensioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecensioneService {

    private final RecensioneRepository recensioneRepository;

    public List<Recensione> findByVino(Long vinoId) {
        return recensioneRepository.findByVinoId(vinoId);
    }

    public List<Recensione> findByUtente(Long utenteId) {
        return recensioneRepository.findByUtenteId(utenteId);
    }

    public boolean haScritto(Long utenteId, Long vinoId) {
        return recensioneRepository.existsByUtenteIdAndVinoId(utenteId, vinoId);
    }

    @Transactional
    public Recensione save(Recensione recensione) {
        if (recensioneRepository.existsByUtenteIdAndVinoId(
                recensione.getUtente().getId(),
                recensione.getVino().getId())) {
            throw new IllegalStateException("Hai già recensito questo vino");
        }
        return recensioneRepository.save(recensione);
    }

    @Transactional
    public void deleteById(Long id) {
        recensioneRepository.deleteById(id);
    }

    public Optional<Recensione> findById(Long id) {
        return recensioneRepository.findById(id);
    }

    public List<Recensione> findAll() {
        return recensioneRepository.findAllWithDetails();
    }

    @Transactional
    public Recensione aggiorna(Recensione recensione) {
        return recensioneRepository.save(recensione); // minuscolo! è il campo, non la classe
    }
}