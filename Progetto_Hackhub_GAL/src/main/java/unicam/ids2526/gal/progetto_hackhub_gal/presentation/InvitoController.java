package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.InvitoHandler;
import unicam.ids2526.gal.progetto_hackhub_gal.core.inviti.Invito;
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
    @GetMapping("/visualizza")
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

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/gestisci/{idInvito}")
    public ResponseEntity<Object> gestisciInvito(Authentication authentication, @PathVariable Long invitoId, @RequestParam boolean esito) {

        String username = authentication.getName();

        try {
            invitoHandler.gestisciInvito(username, invitoId, esito);

            // dichiarazione e inizializzazione del messaggio di risposta
            String messaggioRisposta;

            if (esito) {
                messaggioRisposta = "Invito accettato con successo. Sei stato aggiunto al team!";
            } else {
                messaggioRisposta = "Invito rifiutato.";
            }

            return new ResponseEntity<>(messaggioRisposta, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
