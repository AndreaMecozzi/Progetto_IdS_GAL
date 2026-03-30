package unicam.ids2526.gal.progetto_hackhub_gal.presentation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unicam.ids2526.gal.progetto_hackhub_gal.application.handlers.SegnalazioneHandler;
import org.springframework.security.core.Authentication;
import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.Segnalazione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.SegnalazioneDTO;

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


    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @GetMapping("/visualizza")
    public ResponseEntity<Object> visualizzaSegnalazioni(Authentication authentication) {
        String username = authentication.getName();
        try {
            List<SegnalazioneDTO> segnalazioniDTOs= segnalazioneHandler.visualizzaSegnalazioni(username);
            if (segnalazioniDTOs.isEmpty()) {
                return new ResponseEntity<>("Non ci sono segnalazioni per i tuoi hackathon", HttpStatus.OK);
            }
            return new ResponseEntity<>(segnalazioniDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
