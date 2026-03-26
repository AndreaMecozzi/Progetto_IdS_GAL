package unicam.ids2526.gal.progetto_hackhub_gal.presentation;


import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/segnala")
    public void segnalaTeam(Authentication autenticazione,@RequestParam Long teamID, @RequestParam String motivazione) {
        segnalazioneHandler.segnalaTeam(autenticazione.getName(), teamID, motivazione);
    }

    @GetMapping("/visualizza")
    public List<Segnalazione> visualizzaSegnalazioni(Authentication autenticazione,@RequestParam String nomeHackathon) {
        return segnalazioneHandler.visualizzaSegnalazioni(autenticazione.getName(), nomeHackathon);
    }





}
