package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/vini")
@RequiredArgsConstructor
public class AdminVinoController {

    private final VinoService vinoService;
    private final ProduttoreService produttoreService;
    private final RegioneService regioneService;
    private final CategoriaService categoriaService;
    private final StorageService storageService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("vini", vinoService.findAll());
        return "admin/vini/lista";
    }

    @GetMapping("/nuovo")
    public String formNuovo(Model model) {
        model.addAttribute("vino", new Vino());
        model.addAttribute("produttori", produttoreService.findAll());
        model.addAttribute("regioni", regioneService.findAll());
        model.addAttribute("categorie", categoriaService.findAll());
        return "admin/vini/form";
    }

    @PostMapping("/nuovo")
    public String salva(@Valid @ModelAttribute("vino") Vino vino,
            BindingResult result,
            @RequestParam(value = "immagineFile", required = false) MultipartFile immagineFile,
            @RequestParam(value = "categorieIds", required = false) java.util.List<Long> categorieIds,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("produttori", produttoreService.findAll());
            model.addAttribute("regioni", regioneService.findAll());
            model.addAttribute("categorie", categoriaService.findAll());
            return "admin/vini/form";
        }

        if (immagineFile != null && !immagineFile.isEmpty()) {
            vino.setImmagine(storageService.salva(immagineFile));
        }

        if (categorieIds != null) {
            java.util.List<it.uniroma3.siw.Artie5ive.model.Categoria> cats = new java.util.ArrayList<>();
            for (Long cId : categorieIds) {
                it.uniroma3.siw.Artie5ive.model.Categoria c = categoriaService.findById(cId);
                if (c != null)
                    cats.add(c);
            }
            vino.setCategorie(cats);
        }

        vinoService.save(vino);
        return "redirect:/admin/vini";
    }

    @GetMapping("/modifica/{id}")
    public String formModifica(@PathVariable Long id, Model model) {
        vinoService.findByIdWithDetails(id).ifPresent(v -> model.addAttribute("vino", v));
        model.addAttribute("produttori", produttoreService.findAll());
        model.addAttribute("regioni", regioneService.findAll());
        model.addAttribute("categorie", categoriaService.findAll());
        return "admin/vini/form";
    }

    @PostMapping("/modifica/{id}")
    public String aggiorna(@PathVariable Long id,
            @Valid @ModelAttribute("vino") Vino vino,
            BindingResult result,
            @RequestParam(value = "immagineFile", required = false) MultipartFile immagineFile,
            @RequestParam(value = "categorieIds", required = false) java.util.List<Long> categorieIds,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("produttori", produttoreService.findAll());
            model.addAttribute("regioni", regioneService.findAll());
            model.addAttribute("categorie", categoriaService.findAll());
            return "admin/vini/form";
        }

        vinoService.findById(id).ifPresent(esistente -> {
            if (immagineFile != null && !immagineFile.isEmpty()) {
                storageService.elimina(esistente.getImmagine());
                vino.setImmagine(storageService.salva(immagineFile));
            } else {
                vino.setImmagine(esistente.getImmagine());
            }
        });

        if (categorieIds != null) {
            java.util.List<it.uniroma3.siw.Artie5ive.model.Categoria> cats = new java.util.ArrayList<>();
            for (Long cId : categorieIds) {
                it.uniroma3.siw.Artie5ive.model.Categoria c = categoriaService.findById(cId);
                if (c != null)
                    cats.add(c);
            }
            vino.setCategorie(cats);
        }

        vino.setId(id);
        vinoService.save(vino);
        return "redirect:/admin/vini";
    }

    @PostMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id) {
        vinoService.findById(id).ifPresent(v -> {
            storageService.elimina(v.getImmagine());
            vinoService.deleteById(id);
        });
        return "redirect:/admin/vini";
    }
}