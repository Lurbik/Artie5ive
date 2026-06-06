package it.uniroma3.siw.Artie5ive;

import it.uniroma3.siw.Artie5ive.model.Utente;
import it.uniroma3.siw.Artie5ive.model.RuoloUtente;
import it.uniroma3.siw.Artie5ive.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!utenteRepository.existsByUsername("admin")) {
            Utente admin = new Utente();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNome("Admin");
            admin.setCognome("Sistema");
            admin.setRuolo(RuoloUtente.ADMIN);
            utenteRepository.save(admin);
            System.out.println(">>> Admin creato: admin / admin123");
        }
    }
}