package it.uniroma3.siw.Artie5ive.service;

import it.uniroma3.siw.Artie5ive.model.Utente;
import it.uniroma3.siw.Artie5ive.model.RuoloUtente;
import it.uniroma3.siw.Artie5ive.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Utente registra(Utente utente) {
        if (utenteRepository.existsByUsername(utente.getUsername())) {
            throw new IllegalArgumentException("Username già in uso");
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utente.setRuolo(RuoloUtente.USER);
        return utenteRepository.save(utente);
    }

    @Transactional(readOnly = true)
    public Optional<Utente> findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<Utente> findById(Long id) {
        return utenteRepository.findById(id);
    }

    
   @Transactional(readOnly = true)
public Optional<Utente> findByEmail(String email) {
    return utenteRepository.findByEmail(email);
}
}