package it.uniroma3.siw.Artie5ive.service;

import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.repository.VinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VinoService {

    @Autowired
    private VinoRepository vinoRepository;

    public Iterable<Vino> findAll() {
        return vinoRepository.findAll();
    }

    public Optional<Vino> findById(Long id) {
        return vinoRepository.findById(id);
    }

    public Vino save(Vino vino) {
        return vinoRepository.save(vino);
    }

    public void deleteById(Long id) {
        vinoRepository.deleteById(id);
    }

    public List<Vino> findByAnnata(Integer annata) {
        return vinoRepository.findByAnnata(annata);
    }

    public List<Vino> findByProduttore(Long produttoreId) {
        return vinoRepository.findByProduttoreId(produttoreId);
    }

    public List<Vino> findByRegione(Long regioneId) {
        return vinoRepository.findByRegioneId(regioneId);
    }

    public boolean existsByNomeAndAnnataAndProduttore(String nome, Integer annata, Long produttoreId) {
        return vinoRepository.existsByNomeAndAnnataAndProduttoreId(nome, annata, produttoreId);
    }

    public List<Vino> filtra(Integer annata, Long regioneId, Long produttoreId) {
        if (annata != null && regioneId != null && produttoreId != null) {
            return vinoRepository.findByAnnataAndRegioneIdAndProduttoreId(annata, regioneId, produttoreId);
        } else if (annata != null && regioneId != null) {
            return vinoRepository.findByAnnataAndRegioneId(annata, regioneId);
        } else if (annata != null && produttoreId != null) {
            return vinoRepository.findByAnnataAndProduttoreId(annata, produttoreId);
        } else if (regioneId != null && produttoreId != null) {
            return vinoRepository.findByRegioneIdAndProduttoreId(regioneId, produttoreId);
        } else if (annata != null) {
            return vinoRepository.findByAnnata(annata);
        } else if (regioneId != null) {
            return vinoRepository.findByRegioneId(regioneId);
        } else if (produttoreId != null) {
            return vinoRepository.findByProduttoreId(produttoreId);
        } else {
            return (List<Vino>) vinoRepository.findAll();
        }
    }
}