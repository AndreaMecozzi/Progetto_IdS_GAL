package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.dto.RegistraUtenteDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.UtenteHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.security.JwtUtil;
import unicam.ids2526.gal.progetto_hackhub_gal.security.LoginRequest;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteHandler utenteHandler;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Costruttore con Dependency Injection per i servizi necessari.
     */
    public UtenteController(UtenteHandler utenteHandler,
                            AuthenticationManager authenticationManager,
                            JwtUtil jwtUtil) {
        this.utenteHandler = utenteHandler;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint per l'autenticazione degli utenti.
     * Delega il controllo delle credenziali a Spring Security e restituisce un Token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            //spring security verifica le credenziali
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            //se l'autenticazione ha successo, l'utente viene salvato nel contesto di sicurezza
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // creazione del token JWT con username e ruolo
            String ruolo = utenteHandler.getRuoloByUsername(request.getUsername());
            String jwt = jwtUtil.generateToken(request.getUsername(), ruolo);

            return ResponseEntity.ok(jwt);

        } catch (Exception e) {
            // se le credenziali sono errate, Spring lancia un'eccezione
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide");
        }
    }

    /**
     * Endpoint per la registrazione degli utenti.
     * Delega la validazione, il criptaggio e il salvataggio all'utenteHandler
     */
    @PostMapping("/registra")
    public ResponseEntity<String> registrazione(@RequestBody RegistraUtenteDTO dto) {
        try {
            utenteHandler.registrazione(
                    dto.getEmail(),
                    dto.getUsername(),
                    dto.getPassword(),
                    dto.getRuolo()
            );
            return new ResponseEntity<>("Utente registrato con successo", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}