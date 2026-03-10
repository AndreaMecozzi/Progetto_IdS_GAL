package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.security.JwtUtil;
import unicam.ids2526.gal.progetto_hackhub_gal.security.LoginRequest;

import java.util.Optional;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteRepository utenteRepository;
    private final JwtUtil jwtUtil;

    public UtenteController(UtenteRepository utenteRepository, JwtUtil jwtUtil) {
        this.utenteRepository = utenteRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<Utente> utenteOpt = utenteRepository.findByUsername(request.getUsername());

        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();

            // Confronto della password in chiaro
            if (utente.getPassword().equals(request.getPassword())) {
                String token = jwtUtil.generateToken(utente.getUsername(), utente.getRuolo().name());
                return ResponseEntity.ok(token);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide");
    }
}