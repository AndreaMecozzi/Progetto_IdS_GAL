package unicam.ids2526.gal.progetto_hackhub_gal.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /**
     * metodo chiamato in automatico durante la fase di login.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //viene cercato l'utente nel db tramite la repository
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Attenzione: Utente '" + username + "' non trovato nel database"));

        String ruoloSpring = "ROLE_" + utente.getRuolo().name();

        //L'oggetto "Utente" viene convertito in "User" (UserDetails) in modo che Spring possa interpretarlo
        return new org.springframework.security.core.userdetails.User(
                utente.getUsername(),
                utente.getPassword(), // Questa è la password CRIPTATA salvata nel DB
                Collections.singletonList(new SimpleGrantedAuthority(ruoloSpring))
        );
    }
}