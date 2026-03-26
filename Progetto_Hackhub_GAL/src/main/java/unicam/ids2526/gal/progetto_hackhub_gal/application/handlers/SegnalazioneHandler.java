package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.Segnalazione;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.HackathonRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.SegnalazioneRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.List;

@Service
public class SegnalazioneHandler {

    @Autowired
    private SegnalazioneRepository segnalazioneRep;
    @Autowired
    private UtenteRepository userRep;
    @Autowired
    private TeamRepository teamRep;
    @Autowired
    private HackathonRepository hackathonRep;

    public void segnalaTeam(String username, Long teamID, String motivazione) {
        //TODO: implementare
    }

    public List<Segnalazione> visualizzaSegnalazioni(String username, String nomeHackathon) {
        //TODO: implementare
    }


}
