package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Regione;
import it.uniroma3.siw.Artie5ive.service.RegioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/regioni")
@RequiredArgsConstructor
public class AdminRegioneController {

    private final RegioneService regioneService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("regioni", regioneService.findAll());
        return "admin/regioni/lista";
    }

    @GetMapping("/nuovo")
    public String formNuovo(Model model) {
        model.addAttribute("regione", new Regione());
        return "admin/regioni/form";
    }

    @PostMapping("/nuovo")
    public String salva(@ModelAttribute("regione") Regione regione) {
        regioneService.save(regione);
        return "redirect:/admin/regioni";
    }

    @GetMapping("/modifica/{id}")
    public String formModifica(@PathVariable Long id, Model model) {
        regioneService.findById(id).ifPresent(r -> model.addAttribute("regione", r));
        return "admin/regioni/form";
    }

    @PostMapping("/modifica/{id}")
    public String aggiorna(@PathVariable Long id,
            @ModelAttribute("regione") Regione regione) {
        regione.setId(id);
        regioneService.save(regione);
        return "redirect:/admin/regioni";
    }

    @PostMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id) {
        regioneService.deleteById(id);
        return "redirect:/admin/regioni";
    }
}