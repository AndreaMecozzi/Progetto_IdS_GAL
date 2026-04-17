package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.Segnalazione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.segnalazione.SegnalazioneDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.*;

import java.util.List;

@Service
@Transactional
public class SegnalazioneHandler {
    private SegnalazioneRepository segnalazioneRep;
    private UtenteRepository userRep;
    private TeamRepository teamRep;
    private HackathonRepository hackathonRep;

    public SegnalazioneHandler(SegnalazioneRepository segnalazioneRep,
                               UtenteRepository userRep,
                               TeamRepository teamRep,
                               HackathonRepository hackathonRep) {
        this.segnalazioneRep = segnalazioneRep;
        this.userRep = userRep;
        this.teamRep = teamRep;
        this.hackathonRep = hackathonRep;
    }

    /**
     * Permette a un mentore di segnalare un team partecipante a un hackathon,
     * specificando una motivazione per la segnalazione
     *
     * @param username l'username del mentore che effettua la segnalazione
     * @param nomeTeam il nome del team da segnalare
     * @param motivazione la motivazione della segnalazione inserita dal mentore
     * @throws Exception se il team non esiste, non partecipa ad alcun hackathon,
     *                   l'utente non è un mentore dell'hackathon, l'hackathon è concluso,
     *                   il team non ha effettuato una sottomissione valida oppure
     *                   la motivazione è vuota
     */
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


    /**
     * Restituisce la lista delle segnalazioni relative agli hackathon gestiti
     * da un determinato organizzatore
     *
     * @param username l'username dell'organizzatore che richiede di visualizzare le segnalazioni
     * @return una lista di SegnalazioneDTO contenente le informazioni delle segnalazioni
     * @throws Exception se l'organizzatore non esiste oppure non gestisce alcun hackathon
     */
    public List<SegnalazioneDTO> visualizzaSegnalazioni(String username) throws Exception {
        Utente organizzatore = userRep.findByUsername(username)
                .orElseThrow(() -> new Exception("Errore: l'organizzatore non esiste"));

        List<Hackathon> hackathons = hackathonRep.findByOrganizzatore(organizzatore);
        if (hackathons.isEmpty()) {
            throw new Exception("Errore: l'organizzatore non gestisce hackathon");
        }

        List<Segnalazione> segnalazioni = segnalazioneRep.findByHackathonIn(hackathons);

        // traduziione delle entità in DTO
        return segnalazioni.stream().map(s -> new SegnalazioneDTO(
                s.getSegnalazioneID(),
                s.getMittente().getUsername(),
                s.getMittente().getEmail(),
                s.getMittente().getUserId(),
                s.getTeam().getNome(),
                s.getHackathon().getNome()
        )).toList();
    }




}
