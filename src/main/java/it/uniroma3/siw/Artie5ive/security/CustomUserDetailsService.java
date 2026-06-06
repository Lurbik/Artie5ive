package it.uniroma3.siw.Artie5ive.security;

import it.uniroma3.siw.Artie5ive.model.Utente;
import it.uniroma3.siw.Artie5ive.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(utente.getUsername())
                .password(utente.getPassword())
                .roles(utente.getRuolo().name())
                .build();
    }
}