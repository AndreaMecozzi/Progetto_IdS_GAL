package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.dto.RegistraUtenteDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.UtenteHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.security.LoginRequest;

import java.util.Optional;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteHandler utenteHandler;

    public UtenteController(UtenteHandler utenteHandler) {
        this.utenteHandler = utenteHandler;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<String> token = utenteHandler.login(request.getUsername(), request.getPassword());

        return token
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide"));
    }

    @PostMapping("/registra")
    public ResponseEntity<String> registrazione(@RequestBody RegistraUtenteDTO dto) {
        try{
            utenteHandler.registrazione(
                    dto.getEmail(),
                    dto.getUsername(),
                    dto.getPassword(),
                    dto.getRuolo()
            );
            return new ResponseEntity<>("Utente registrato con successo",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}