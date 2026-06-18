package it.uniroma3.siw.Artie5ive.service;

import it.uniroma3.siw.Artie5ive.model.Regione;
import it.uniroma3.siw.Artie5ive.repository.RegioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class RegioneService {

    @Autowired
    private RegioneRepository regioneRepository;

    @Transactional(readOnly = true)
    public Iterable<Regione> findAll() {
        return regioneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Regione> findById(Long id) {
        return regioneRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByNome(String nome) {
        return regioneRepository.existsByNome(nome);
    }

    @Transactional
    public Regione save(Regione regione) {
        return regioneRepository.save(regione);
    }

    @Transactional
    public void deleteById(Long id) {
        regioneRepository.deleteById(id);
    }
}