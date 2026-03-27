package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.Segnalazione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
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

    public void segnalaTeam(String username, String nomeTeam, String motivazione) throws Exception{
        // recupera del team da segnalare
        Team team = teamRep.findByNome(nomeTeam)
                .orElseThrow(()->new Exception("Errore: il team non esiste"));

        // controllo di appartenenza e stato dell'hackathon
        Hackathon hackathon = team.getHackathon();
        if(hackathon == null){
            throw new Exception ("Errore: il team non partecipa a nessun hackathon");
        }

        Utente segnalatore = userRep.findByUsername(username)
                .orElseThrow(()->new Exception("Errore: il mentore non esiste"));
        if(!hackathon.getMentori().contains(segnalatore)){
            throw new Exception ("Errore: non sei nominato mentore per questo hackathon");
        }

        if(hackathon.getStato().equals("CONCLUSO")){
            throw new Exception ("Errore: impossibile segnalare il team");
        }

        // controllo presenza lavoro del team
        if(team.getSottomissione()==null){
            throw new Exception ("Errore: impossibile segnalare il team, non ha sottomissioni");
        }else if (team.getSottomissione().getFile()==null) {
            throw new Exception ("Errore: impossibile segnalare il team, non ha lavori caricati");
        }

        // controllo sulla motivazione inserita dal mentore
        if(motivazione.isEmpty()){
            throw new Exception ("Errore: motivazione non valida");
        }

        // creazione e salvataggio della segnalazione
        Segnalazione segnalazione= new Segnalazione (segnalatore,team,motivazione,hackathon);
        segnalazioneRep.save(segnalazione);

    }


    public List<Segnalazione> visualizzaSegnalazioni(String username) throws Exception {
        Utente organizzatore = userRep.findByUsername(username)
                .orElseThrow(() -> new Exception("Errore: l'organizzatore non esiste"));

        List<Hackathon> hackathons = hackathonRep.findByOrganizzatore(organizzatore);
        if (hackathons.isEmpty()) {
            throw new Exception("Errore: l'organizzatore non gestisce hackathon");
        }

        return segnalazioneRep.findByHackathonIn(hackathons);
    }




}
