package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.security.JwtUtil;
import java.util.Optional;

@Service
public class UtenteHandler {

    private final UtenteRepository utenteRepository;
    private final JwtUtil jwtUtil;

    public UtenteHandler(UtenteRepository utenteRepository, JwtUtil jwtUtil) {
        this.utenteRepository = utenteRepository;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> login(String username, String password) {
        Optional<Utente> utenteOpt = utenteRepository.findByUsername(username);

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