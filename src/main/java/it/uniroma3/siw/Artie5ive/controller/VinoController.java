package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.service.VinoService;
import it.uniroma3.siw.Artie5ive.service.ProduttoreService;
import it.uniroma3.siw.Artie5ive.service.RegioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.Artie5ive.service.RecensioneService;

@Controller
@RequiredArgsConstructor
public class VinoController {

    private final VinoService vinoService;
    private final ProduttoreService produttoreService;
    private final RegioneService regioneService;
    final RecensioneService recensioneService;

    @GetMapping("/vini")
    public String listaVini(Model model) {
        model.addAttribute("vini", vinoService.findAll());
        model.addAttribute("regioni", regioneService.findAll());
        model.addAttribute("produttori", produttoreService.findAll());
        return "vini/lista";
    }

    @GetMapping("/vini/{id}")
    public String dettaglioVino(@PathVariable Long id, Model model) {
        return vinoService.findById(id).map(vino -> {
            model.addAttribute("vino", vino);
            model.addAttribute("recensioni", recensioneService.findByVino(id)); // ← usa il service, non
                                                                                // vino.getRecensioni()
            return "vini/dettaglio";
        }).orElse("redirect:/vini");
    }

    @GetMapping("/vini/filtra")
    public String filtra(
            @RequestParam(required = false) Integer annata,
            @RequestParam(required = false) Long regioneId,
            @RequestParam(required = false) Long produttoreId,
            Model model) {
        model.addAttribute("vini", vinoService.filtra(annata, regioneId, produttoreId));
        model.addAttribute("regioni", regioneService.findAll());
        model.addAttribute("produttori", produttoreService.findAll());
        return "vini/lista";
    }
}