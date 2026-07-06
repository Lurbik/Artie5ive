package it.uniroma3.siw.Artie5ive.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.service.VinoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final VinoService vinoService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/react-vini")
    public String reactVini() {
        return "react-vini";
    }

    @GetMapping("/analisi")
    public String analisi(Model model) {
        model.addAttribute("numVini", vinoService.contaVini());
        model.addAttribute("tempoLazy", vinoService.misuraTempoLazy() + " ms");
        model.addAttribute("tempoJoinFetch", vinoService.misuraTempoJoinFetch() + " ms");
        model.addAttribute("numQuery", vinoService.contaVini() + 1);
        return "analisi";
    }
}