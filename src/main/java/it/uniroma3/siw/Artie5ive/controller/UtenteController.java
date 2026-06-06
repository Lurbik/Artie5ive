package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Utente;
import it.uniroma3.siw.Artie5ive.service.UtenteService;
import it.uniroma3.siw.Artie5ive.service.RecensioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final RecensioneService recensioneService;

    // Form registrazione
    @GetMapping("/registrazione")
    public String formRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "utente/registrazione";
    }

    // Salva nuovo utente
    @PostMapping("/registrazione")
    public String registra(@Valid @ModelAttribute("utente") Utente utente,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "utente/registrazione";
        }
        try {
            utenteService.registra(utente);
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", "error.utente", e.getMessage());
            return "utente/registrazione";
        }
        return "redirect:/login";
    }

    // Profilo utente loggato
    @GetMapping("/profilo")
    public String profilo(Principal principal, Model model) {
        utenteService.findByUsername(principal.getName()).ifPresent(utente -> {
            model.addAttribute("utente", utente);
            model.addAttribute("recensioni",
                recensioneService.findByUtente(utente.getId()));
        });
        return "utente/profilo";
    }
}