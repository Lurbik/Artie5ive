package it.uniroma3.siw.Artie5ive.security;

import it.uniroma3.siw.Artie5ive.model.RuoloUtente;
import it.uniroma3.siw.Artie5ive.model.Utente;
import it.uniroma3.siw.Artie5ive.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);

        String email = oAuth2User.getAttribute("email");
        String nome = oAuth2User.getAttribute("given_name");
        String cognome = oAuth2User.getAttribute("family_name");

        System.out.println(">>> OAUTH EMAIL: " + email);
        System.out.println(">>> ESISTE GIA: " + utenteRepository.existsByEmail(email));

        if (!utenteRepository.existsByEmail(email)) {
            try {
                Utente utente = new Utente();
                utente.setUsername(email);
                utente.setEmail(email);
                utente.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                utente.setNome(nome != null ? nome : "Utente");
                utente.setCognome(cognome != null ? cognome : "Google");
                utente.setRuolo(RuoloUtente.USER);
                System.out.println(">>> STO PER SALVARE: " + email);
                utenteRepository.save(utente);
                System.out.println(">>> SALVATO!");
            } catch (Exception e) {
                System.out.println(">>> ERRORE SAVE: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return new org.springframework.security.oauth2.core.user.DefaultOAuth2User(
            oAuth2User.getAuthorities(),
            oAuth2User.getAttributes(),
            "email"
        );
    }
}