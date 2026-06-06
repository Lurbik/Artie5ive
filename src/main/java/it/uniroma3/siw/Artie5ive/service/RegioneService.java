package it.uniroma3.siw.Artie5ive.service;

import it.uniroma3.siw.Artie5ive.model.Regione;
import it.uniroma3.siw.Artie5ive.repository.RegioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RegioneService {

    @Autowired
    private RegioneRepository regioneRepository;

    public Iterable<Regione> findAll() {
        return regioneRepository.findAll();
    }

    public Optional<Regione> findById(Long id) {
        return regioneRepository.findById(id);
    }

    public Regione save(Regione regione) {
        return regioneRepository.save(regione);
    }

    public void deleteById(Long id) {
        regioneRepository.deleteById(id);
    }

    public boolean existsByNome(String nome) {
        return regioneRepository.existsByNome(nome);
    }
}