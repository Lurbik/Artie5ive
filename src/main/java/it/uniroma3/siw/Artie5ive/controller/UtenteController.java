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
import org.springframework.security.core.Authentication;

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

    @GetMapping("/profilo")
    public String profilo(Authentication authentication, Model model) {
        if (authentication == null)
            return "redirect:/login";

        String identifier;
        if (authentication
                .getPrincipal() instanceof org.springframework.security.oauth2.core.user.OAuth2User oauth2User) {
            identifier = oauth2User.getAttribute("email");
        } else {
            identifier = authentication.getName();
        }

        var utente = utenteService.findByUsername(identifier)
                .or(() -> utenteService.findByEmail(identifier));

        if (utente.isEmpty())
            return "redirect:/"; // invece di crashare

        utente.ifPresent(u -> {
            model.addAttribute("utente", u);
            model.addAttribute("recensioni", recensioneService.findByUtente(u.getId()));
        });

        return "utente/profilo";
    }
}