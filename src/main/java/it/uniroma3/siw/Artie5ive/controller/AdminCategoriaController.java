package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.model.Categoria;
import it.uniroma3.siw.Artie5ive.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categorie")
@RequiredArgsConstructor
public class AdminCategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("categorie", categoriaService.findAll());
        return "admin/categorie/lista";
    }

    @GetMapping("/nuovo")
    public String formNuovo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorie/form";
    }

    @PostMapping("/nuovo")
    public String salva(@ModelAttribute("categoria") Categoria categoria) {
        categoriaService.save(categoria);
        return "redirect:/admin/categorie";
    }

    @GetMapping("/modifica/{id}")
    public String formModifica(@PathVariable Long id, Model model) {
        Categoria c = categoriaService.findById(id);
        if (c != null)
            model.addAttribute("categoria", c);
        return "admin/categorie/form";
    }

    @PostMapping("/modifica/{id}")
    public String aggiorna(@PathVariable Long id,
            @ModelAttribute("categoria") Categoria categoria) {
        categoria.setId(id);
        categoriaService.save(categoria);
        return "redirect:/admin/categorie";
    }

    @PostMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return "redirect:/admin/categorie";
    }
}