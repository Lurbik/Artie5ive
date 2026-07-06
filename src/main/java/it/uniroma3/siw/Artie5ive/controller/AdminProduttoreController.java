package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Produttore;
import it.uniroma3.siw.Artie5ive.service.ProduttoreService;
import it.uniroma3.siw.Artie5ive.service.RegioneService;
import it.uniroma3.siw.Artie5ive.service.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/produttori")
@RequiredArgsConstructor
public class AdminProduttoreController {

    private final ProduttoreService produttoreService;
    private final RegioneService regioneService;
    private final StorageService storageService;

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
            BindingResult result,
            @RequestParam(value = "immagineFile", required = false) MultipartFile immagineFile,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regioni", regioneService.findAll());
            return "admin/produttori/form";
        }
        if (immagineFile != null && !immagineFile.isEmpty()) {
            produttore.setImmagine(storageService.salva(immagineFile));
        }
        produttoreService.save(produttore);
        return "redirect:/admin/produttori";
    }

    @GetMapping("/modifica/{id}")
    public String formModifica(@PathVariable Long id, Model model) {
        produttoreService.findById(id).ifPresent(p -> model.addAttribute("produttore", p));
        model.addAttribute("regioni", regioneService.findAll());
        return "admin/produttori/form";
    }

    @PostMapping("/modifica/{id}")
    public String aggiorna(@PathVariable Long id,
            @Valid @ModelAttribute("produttore") Produttore produttore,
            BindingResult result,
            @RequestParam(value = "immagineFile", required = false) MultipartFile immagineFile,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("regioni", regioneService.findAll());
            return "admin/produttori/form";
        }
        produttoreService.findById(id).ifPresent(esistente -> {
            if (immagineFile != null && !immagineFile.isEmpty()) {
                storageService.elimina(esistente.getImmagine());
                produttore.setImmagine(storageService.salva(immagineFile));
            } else {
                produttore.setImmagine(esistente.getImmagine());
            }
        });
        produttore.setId(id);
        produttoreService.save(produttore);
        return "redirect:/admin/produttori";
    }

    @PostMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            produttoreService.findById(id).ifPresent(p -> {
                produttoreService.deleteById(id); // prima elimina dal DB
                storageService.elimina(p.getImmagine()); // poi elimina immagine
            });
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errore",
                    "Impossibile eliminare: ci sono vini collegati a questo produttore.");
        }
        return "redirect:/admin/produttori";
    }
}