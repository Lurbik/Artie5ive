package it.uniroma3.siw.Artie5ive.service;

import it.uniroma3.siw.Artie5ive.model.Produttore;
import it.uniroma3.siw.Artie5ive.repository.ProduttoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ProduttoreService {

    @Autowired
    private ProduttoreRepository produttoreRepository;

    @Transactional(readOnly = true)
    public Iterable<Produttore> findAll() {
        return produttoreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Produttore> findById(Long id) {
        return produttoreRepository.findById(id);
    }

    @Transactional
    public Produttore save(Produttore produttore) {
        return produttoreRepository.save(produttore);
    }

    @Transactional
    public void deleteById(Long id) {
        produttoreRepository.deleteById(id);
    }
}