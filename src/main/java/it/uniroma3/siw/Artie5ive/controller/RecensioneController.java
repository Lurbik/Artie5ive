package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Recensione;
import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.service.RecensioneService;
import it.uniroma3.siw.Artie5ive.service.UtenteService;
import it.uniroma3.siw.Artie5ive.service.VinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/recensioni")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService recensioneService;
    private final UtenteService utenteService;
    private final VinoService vinoService;

    // Form nuova recensione
    @GetMapping("/nuova/{vinoId}")
    public String formNuova(@PathVariable Long vinoId, Model model, Principal principal) {
        // Controlla che non abbia già recensito
        utenteService.findByUsername(principal.getName()).ifPresent(utente -> {
            if (recensioneService.haScritto(utente.getId(), vinoId)) {
                model.addAttribute("errore", "Hai già recensito questo vino");
            }
        });
        model.addAttribute("recensione", new Recensione());
        model.addAttribute("vinoId", vinoId);
        return "recensioni/form";
    }

    // Salva nuova recensione
    @PostMapping("/nuova/{vinoId}")
    public String salva(@PathVariable Long vinoId,
                        @Valid @ModelAttribute("recensione") Recensione recensione,
                        BindingResult result,
                        Principal principal) {
        if (result.hasErrors()) {
            return "recensioni/form";
        }
        utenteService.findByUsername(principal.getName()).ifPresent(utente -> {
            recensione.setUtente(utente);
            vinoService.findById(vinoId).ifPresent(recensione::setVino);
            recensioneService.save(recensione);
        });
        return "redirect:/vini/" + vinoId;
    }

    // Elimina recensione propria
    @PostMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id, Principal principal) {
        recensioneService.findById(id).ifPresent(rec -> {
            if (rec.getUtente().getUsername().equals(principal.getName())) {
                recensioneService.deleteById(id);
            }
        });
        return "redirect:/profilo";
    }
}