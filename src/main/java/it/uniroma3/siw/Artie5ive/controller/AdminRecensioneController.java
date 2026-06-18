package it.uniroma3.siw.Artie5ive.controller;

import it.uniroma3.siw.Artie5ive.service.RecensioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/recensioni")
@RequiredArgsConstructor
public class AdminRecensioneController {

    private final RecensioneService recensioneService;

    @PostMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id) {
        recensioneService.deleteById(id);
        return "redirect:/admin/recensioni";
    }

    @GetMapping
    public String lista(org.springframework.ui.Model model) {
        model.addAttribute("recensioni", recensioneService.findAll());
        return "admin/recensioni/lista";
    }
}