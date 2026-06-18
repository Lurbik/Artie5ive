package it.uniroma3.siw.Artie5ive;

import it.uniroma3.siw.Artie5ive.model.Utente;
import it.uniroma3.siw.Artie5ive.model.RuoloUtente;
import it.uniroma3.siw.Artie5ive.model.Vino;
import it.uniroma3.siw.Artie5ive.model.Produttore;
import it.uniroma3.siw.Artie5ive.model.Regione;
import it.uniroma3.siw.Artie5ive.model.ColoreVino;
import it.uniroma3.siw.Artie5ive.model.StileVino;
import it.uniroma3.siw.Artie5ive.repository.UtenteRepository;
import it.uniroma3.siw.Artie5ive.repository.VinoRepository;
import it.uniroma3.siw.Artie5ive.repository.ProduttoreRepository;
import it.uniroma3.siw.Artie5ive.repository.RegioneRepository;
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
    @Autowired
    private VinoRepository vinoRepository;
    @Autowired
    private ProduttoreRepository produttoreRepository;
    @Autowired
    private RegioneRepository regioneRepository;

    @Override
    public void run(String... args) throws Exception {

        // Admin
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

        // Utente di test
        if (!utenteRepository.existsByUsername("mario")) {
            Utente user = new Utente();
            user.setUsername("mario");
            user.setPassword(passwordEncoder.encode("mario123"));
            user.setNome("Mario");
            user.setCognome("Rossi");
            user.setRuolo(RuoloUtente.USER);
            utenteRepository.save(user);
        }

        // Dati solo se il DB è vuoto
        if (!regioneRepository.existsByNome("Toscana")) {

            // Regioni
            Regione toscana = new Regione();
            toscana.setNome("Toscana");
            regioneRepository.save(toscana);

            Regione piemonte = new Regione();
            piemonte.setNome("Piemonte");
            regioneRepository.save(piemonte);

            Regione sicilia = new Regione();
            sicilia.setNome("Sicilia");
            regioneRepository.save(sicilia);

            // Produttori
            Produttore antinori = new Produttore();
            antinori.setNome("Antinori");
            antinori.setDescrizione("Storica famiglia vinicola toscana dal 1385.");
            antinori.setAnnoFondazione(1385);
            antinori.setRegione(toscana);
            produttoreRepository.save(antinori);

            Produttore gaja = new Produttore();
            gaja.setNome("Gaja");
            gaja.setDescrizione("Eccellenza del Piemonte, famoso per il Barolo.");
            gaja.setAnnoFondazione(1859);
            gaja.setRegione(piemonte);
            produttoreRepository.save(gaja);

            // Vini
            Vino tignanello = new Vino();
            tignanello.setNome("Tignanello");
            tignanello.setAnnata(2019);
            tignanello.setDescrizione("Supertuscan iconico, blend di Sangiovese e Cabernet.");
            tignanello.setGradazione(13.5);
            tignanello.setColore(ColoreVino.ROSSO);
            tignanello.setStile(StileVino.FERMO);
            tignanello.setProduttore(antinori);
            tignanello.setRegione(toscana);
            vinoRepository.save(tignanello);

            Vino chianti = new Vino();
            chianti.setNome("Chianti Classico");
            chianti.setAnnata(2020);
            chianti.setDescrizione("Il classico toscano, fresco e beverino.");
            chianti.setGradazione(13.0);
            chianti.setColore(ColoreVino.ROSSO);
            chianti.setStile(StileVino.FERMO);
            chianti.setProduttore(antinori);
            chianti.setRegione(toscana);
            vinoRepository.save(chianti);

            Vino barolo = new Vino();
            barolo.setNome("Barolo DOCG");
            barolo.setAnnata(2018);
            barolo.setDescrizione("Il re dei vini italiani, potente e longevo.");
            barolo.setGradazione(14.5);
            barolo.setColore(ColoreVino.ROSSO);
            barolo.setStile(StileVino.FERMO);
            barolo.setProduttore(gaja);
            barolo.setRegione(piemonte);
            vinoRepository.save(barolo);

            Vino prosecco = new Vino();
            prosecco.setNome("Prosecco Superiore");
            prosecco.setAnnata(2022);
            prosecco.setDescrizione("Bollicine eleganti, ideale per aperitivo.");
            prosecco.setGradazione(11.0);
            prosecco.setColore(ColoreVino.BIANCO);
            prosecco.setStile(StileVino.SPUMANTE);
            prosecco.setProduttore(antinori);
            prosecco.setRegione(toscana);
            vinoRepository.save(prosecco);

            System.out.println(">>> Dati di test inseriti!");
        }
    }
}