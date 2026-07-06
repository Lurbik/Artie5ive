package it.uniroma3.siw.Artie5ive.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.repository.VinoRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final VinoRepository vinoRepository;

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
        // LAZY — simula N+1
        long inizioLazy = System.currentTimeMillis();
        List<Vino> viniLazy = vinoRepository.findAll();
        // accesso lazy alle relazioni
        viniLazy.forEach(v -> {
            if (v.getProduttore() != null)
                v.getProduttore().getNome();
        });
        long tempoLazy = System.currentTimeMillis() - inizioLazy;

        // JOIN FETCH
        long inizioJoin = System.currentTimeMillis();
        List<Vino> viniJoin = vinoRepository.findAllWithProduttore();
        long tempoJoin = System.currentTimeMillis() - inizioJoin;

        model.addAttribute("numVini", viniLazy.size());
        model.addAttribute("tempoLazy", tempoLazy + " ms");
        model.addAttribute("tempoJoinFetch", tempoJoin + " ms");
        model.addAttribute("numQuery", viniLazy.size() + 1);
        return "analisi";
    }

}