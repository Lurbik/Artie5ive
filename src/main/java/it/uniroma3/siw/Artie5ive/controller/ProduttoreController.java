package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.service.ProduttoreService;
import it.uniroma3.siw.Artie5ive.service.VinoService;
import it.uniroma3.siw.Artie5ive.model.Produttore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProduttoreController {

    private final ProduttoreService produttoreService;
    private final VinoService vinoService;

    @GetMapping("/produttori")
    public String lista(Model model) {
        List<Produttore> produttori = (List<Produttore>) produttoreService.findAll();
        Map<Long, Long> conteggioVini = new HashMap<>();
        for (Produttore p : produttori) {
            conteggioVini.put(p.getId(), vinoService.contaPerProduttore(p.getId()));
        }
        model.addAttribute("produttori", produttori);
        model.addAttribute("conteggioVini", conteggioVini);
        return "produttori/lista";
    }

    @GetMapping("/produttori/{id}")
    public String dettaglio(@PathVariable Long id, Model model) {
        return produttoreService.findById(id).map(p -> {
            model.addAttribute("produttore", p);
            model.addAttribute("vini", vinoService.findByProduttore(id));
            return "produttori/dettaglio";
        }).orElse("redirect:/produttori");
    }
}