package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.security.JwtUtil;
import java.util.Optional;

@Service
public class UtenteHandler {

    private final UtenteRepository utenteRep;
    private final JwtUtil jwtUtil;

    public UtenteHandler(UtenteRepository utenteRep, JwtUtil jwtUtil) {
        this.utenteRep = utenteRep;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> login(String username, String password) {
        Optional<Utente> utenteOpt = utenteRep.findByUsername(username);

        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            // Logica di business: confronto password e generazione token
            if (utente.getPassword().equals(password)) {
                return Optional.of(jwtUtil.generateToken(utente.getUsername(), utente.getRuolo().name()));
            }
        }
        return Optional.empty();
    }
}