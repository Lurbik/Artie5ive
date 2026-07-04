package it.uniroma3.siw.Artie5ive.service;

import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.repository.VinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VinoService {

    @Autowired
    private VinoRepository vinoRepository;

    @Transactional(readOnly = true)
    public Iterable<Vino> findAll() {
        return vinoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vino> findById(Long id) {
        return vinoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Vino> findByIdWithDetails(Long id) {
        return vinoRepository.findByIdWithDetails(id);
    }

    @Transactional(readOnly = true)
    public List<Vino> findByAnnata(Integer annata) {
        return vinoRepository.findByAnnata(annata);
    }

    @Transactional(readOnly = true)
    public List<Vino> findByProduttore(Long produttoreId) {
        return vinoRepository.findByProduttoreId(produttoreId);
    }

    @Transactional(readOnly = true)
    public List<Vino> findByRegione(Long regioneId) {
        return vinoRepository.findByRegioneId(regioneId);
    }

    @Transactional(readOnly = true)
    public boolean existsByNomeAndAnnataAndProduttore(String nome, Integer annata, Long produttoreId) {
        return vinoRepository.existsByNomeAndAnnataAndProduttoreId(nome, annata, produttoreId);
    }

    @Transactional(readOnly = true)
    public List<Vino> filtra(Integer annata, Long regioneId, Long produttoreId) {
        if (annata != null && regioneId != null && produttoreId != null)
            return vinoRepository.findByAnnataAndRegioneIdAndProduttoreId(annata, regioneId, produttoreId);
        if (annata != null && regioneId != null)
            return vinoRepository.findByAnnataAndRegioneId(annata, regioneId);
        if (annata != null && produttoreId != null)
            return vinoRepository.findByAnnataAndProduttoreId(annata, produttoreId);
        if (regioneId != null && produttoreId != null)
            return vinoRepository.findByRegioneIdAndProduttoreId(regioneId, produttoreId);
        if (annata != null)
            return vinoRepository.findByAnnata(annata);
        if (regioneId != null)
            return vinoRepository.findByRegioneId(regioneId);
        if (produttoreId != null)
            return vinoRepository.findByProduttoreId(produttoreId);
        return (List<Vino>) vinoRepository.findAll();
    }

    @Transactional
    public Vino save(Vino vino) {
        return vinoRepository.save(vino);
    }

    @Transactional
    public void deleteById(Long id) {
        vinoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Long contaPerProduttore(Long produttoreId) {
        return vinoRepository.countByProduttoreId(produttoreId);
    }
}