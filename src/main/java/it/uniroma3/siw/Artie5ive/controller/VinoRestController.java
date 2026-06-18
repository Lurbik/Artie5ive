package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.model.Recensione;
import it.uniroma3.siw.Artie5ive.service.VinoService;
import it.uniroma3.siw.Artie5ive.service.RecensioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VinoRestController {

    private final VinoService vinoService;
    private final RecensioneService recensioneService;

    @GetMapping("/vini")
    public List<VinoDTO> getVini() {
        return StreamSupport.stream(vinoService.findAll().spliterator(), false)
                .map(VinoDTO::from)
                .toList();
    }

    @GetMapping("/vini/{id}")
    public VinoDTO getVino(@PathVariable Long id) {
        return vinoService.findById(id)
                .map(VinoDTO::from)
                .orElse(null);
    }

    @GetMapping("/vini/{id}/recensioni")
    public List<RecensioneDTO> getRecensioni(@PathVariable Long id) {
        return recensioneService.findByVino(id).stream()
                .map(RecensioneDTO::from)
                .toList();
    }

    // DTO interni — evitano problemi di lazy loading nella serializzazione JSON
    record VinoDTO(
            Long id, String nome, Integer annata, String descrizione,
            String colore, String stile, Double gradazione,
            String immagine, String produttore, String regione) {
        static VinoDTO from(Vino v) {
            return new VinoDTO(
                    v.getId(), v.getNome(), v.getAnnata(), v.getDescrizione(),
                    v.getColore() != null ? v.getColore().name() : null,
                    v.getStile() != null ? v.getStile().name() : null,
                    v.getGradazione(),
                    v.getImmagine(),
                    v.getProduttore() != null ? v.getProduttore().getNome() : null,
                    v.getRegione() != null ? v.getRegione().getNome() : null);
        }
    }

    record RecensioneDTO(Long id, String utente, Integer voto, String testo, String data) {
        static RecensioneDTO from(Recensione r) {
            return new RecensioneDTO(
                    r.getId(),
                    r.getUtente().getUsername(),
                    r.getVoto(),
                    r.getTesto(),
                    r.getData() != null ? r.getData().toString() : null);
        }
    }
}