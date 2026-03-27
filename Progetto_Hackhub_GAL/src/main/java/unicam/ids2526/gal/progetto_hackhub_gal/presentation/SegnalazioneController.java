package unicam.ids2526.gal.progetto_hackhub_gal.presentation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.SegnalazioneHandler;
import org.springframework.security.core.Authentication;
import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.Segnalazione;

import java.util.List;

@RestController
@RequestMapping("/segnalazioni")
public class SegnalazioneController {

    @Autowired
    private SegnalazioneHandler segnalazioneHandler;

    @PreAuthorize("hasAuthority('MENTORE')")
    @PostMapping("/segnala")
    public ResponseEntity<Object>  segnalaTeam(Authentication authentication,@RequestParam String nomeTeam, @RequestParam String motivazione) {
        String username = authentication.getName();
        try {
            segnalazioneHandler.segnalaTeam(username, nomeTeam, motivazione);
            return new ResponseEntity<>("Team segnalato", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
    @GetMapping("/visualizza")
    public List<Segnalazione> visualizzaSegnalazioni(Authentication autenticazione,@RequestParam String nomeHackathon) {
        return segnalazioneHandler.visualizzaSegnalazioni(autenticazione.getName(), nomeHackathon);
    }


     */




}
