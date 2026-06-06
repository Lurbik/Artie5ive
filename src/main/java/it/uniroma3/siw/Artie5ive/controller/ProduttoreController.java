package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.service.ProduttoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProduttoreController {

    private final ProduttoreService produttoreService;

    @GetMapping("/produttori")
    public String lista(Model model) {
        model.addAttribute("produttori", produttoreService.findAll());
        return "produttori/lista";
    }

    @GetMapping("/produttori/{id}")
    public String dettaglio(@PathVariable Long id, Model model) {
        produttoreService.findById(id).ifPresent(p ->
            model.addAttribute("produttore", p));
        return "produttori/dettaglio";
    }
}