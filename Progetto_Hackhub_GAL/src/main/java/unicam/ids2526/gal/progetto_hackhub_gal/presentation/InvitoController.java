package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.InvitoHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Invito;
import org.springframework.security.core.Authentication;

import java.util.List;


@RestController
@RequestMapping("/inviti")
public class InvitoController {
    private final InvitoHandler invitoHandler;

    public InvitoController(InvitoHandler utenteHandler) {
        this.invitoHandler = utenteHandler;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/invita")
    public ResponseEntity<Object> invitaUtente(Authentication authentication,@RequestBody String userRicevente){
        String userMittente = authentication.getName();
        try{
            invitoHandler.invitaUtente(userMittente, userRicevente);
            return new ResponseEntity<>("Invito inviato!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/visualizzaInviti")
    public ResponseEntity<Object> visualizzaInviti(Authentication autentication){
        String username = autentication.getName();
        try {
         //chiama il metodo dell'handler
            List<Invito> inviti = invitoHandler.visualizzaInviti(username);
            if (inviti == null) {
                return new ResponseEntity<>("Non ci sono inviti", HttpStatus.OK);
            }
            return new ResponseEntity<>(inviti, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
