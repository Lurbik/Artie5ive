package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Produttore;
import it.uniroma3.siw.Artie5ive.service.ProduttoreService;
import it.uniroma3.siw.Artie5ive.service.RegioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/produttori")
@RequiredArgsConstructor
public class AdminProduttoreController {

    private final ProduttoreService produttoreService;
    private final RegioneService regioneService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("produttori", produttoreService.findAll());
        return "admin/produttori/lista";
    }

    @GetMapping("/nuovo")
    public String formNuovo(Model model) {
        model.addAttribute("produttore", new Produttore());
        model.addAttribute("regioni", regioneService.findAll());
        return "admin/produttori/form";
    }

    @PostMapping("/nuovo")
    public String salva(@Valid @ModelAttribute("produttore") Produttore produttore,
                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regioni", regioneService.findAll());
            return "admin/produttori/form";
        }
        produttoreService.save(produttore);
        return "redirect:/admin/produttori";
    }

    @GetMapping("/modifica/{id}")
    public String formModifica(@PathVariable Long id, Model model) {
        produttoreService.findById(id).ifPresent(p ->
            model.addAttribute("produttore", p));
        model.addAttribute("regioni", regioneService.findAll());
        return "admin/produttori/form";
    }

    @PostMapping("/modifica/{id}")
    public String aggiorna(@PathVariable Long id,
                           @Valid @ModelAttribute("produttore") Produttore produttore,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regioni", regioneService.findAll());
            return "admin/produttori/form";
        }
        produttore.setId(id);
        produttoreService.save(produttore);
        return "redirect:/admin/produttori";
    }

    @PostMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id) {
        produttoreService.deleteById(id);
        return "redirect:/admin/produttori";
    }
}