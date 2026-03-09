package unicam.ids2526.gal.progetto_hackhub_gal.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.InvitoHandler;

@RestController
@RequestMapping("/inviti")
public class InvitoController {
    private final InvitoHandler invitoHandler;

    public InvitoController(InvitoHandler utenteHandler) {
        this.invitoHandler = utenteHandler;
    }

    @PreAuthorize("hasAuthority('UTENTE')") /// Da testare e rivedere
    @PostMapping("/invita/{userMittente}")
    public ResponseEntity<Object> invitaUtente(@PathVariable String userMittente,@RequestBody String userRicevente){
        try{
            invitoHandler.invitaUtente(userMittente, userRicevente);
            return new ResponseEntity<>("Invito inviato!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
