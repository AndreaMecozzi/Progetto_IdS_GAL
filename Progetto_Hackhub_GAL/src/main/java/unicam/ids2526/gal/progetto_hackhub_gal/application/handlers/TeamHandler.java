package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

@Service
public class TeamHandler {
    private final TeamRepository teamRep;
    private final UtenteRepository utenteRep;

    public TeamHandler(TeamRepository teamRepository, UtenteRepository utenteRep) {
        this.teamRep = teamRepository;
        this.utenteRep = utenteRep;
    }

    /**
     * Crea un nuovo team a partire dal nome specificato dall'utente
     *
     * @param username l'utente che vuole creare il nuovo team
     * @param nomeTeam il nome del team che deve essere creato
     * @throws Exception se qualche condizione non viene rispettata
     */
    public void creaTeam(String username, String nomeTeam) throws Exception{
        if(teamRep.findByUtenti_Username(username).isPresent()){
            throw new Exception("Errore: Fai già parte di un team");
        }

        if(teamRep.findByNome(nomeTeam).isPresent()){
            throw new Exception("Errore: Esiste già un team con il nome passato");
        }

        Utente utente=utenteRep.findByUsername(username).orElseThrow();
        Team team = new Team(nomeTeam, utente);
        teamRep.save(team);
    }

    /**
     * Restituisce le info del team di cui fa parte l'utente che esegue la richiesta
     *
     * @param username l'username dell'utente che vuole visualizzare le info del proprio team
     * @return team
     * @throws Exception se il team non esiste o l'utente non è membro del team
     */

    public Team visualizzaTeam(String username) throws Exception{
        Team team = teamRep.findByUtenti_Username(username)
                .orElseThrow(()->new Exception("Errore: Non appartieni a nessun team"));

        return team;

    }


    /**
     * Modifica le informazioni del Team
     *
     * @param username l'username dell'utente che vuole modificare le info del proprio team
     * @param nuovoNome il nuovo nome che si vuole dare al team
     * @return void
     * @throws Exception se il team non esiste, l'utente non è membro di un team, o i parametri non sono validi
     */

    public void modificaTeam(String username, String nuovoNome) throws Exception{
        Team mioTeam= teamRep.findByUtenti_Username(username).
                orElseThrow(()->new Exception("Errore: Non appartieni a nessun team"));

        Team team = teamRep.findByUtenti_Username(username)
                .orElseThrow(()->new Exception("Errore: Esiste già un team con questo nome"));

        Hackathon hackathon = mioTeam.getHackathon();
        if(hackathon != null){
            if(!hackathon.getStato().equals("IN_ISCRIZIONE")){
                throw new Exception("Errore: impossibile modificare il team");
            }
        }

        mioTeam.setNome(nuovoNome);
        teamRep.save(mioTeam);
    }


    /**
     * Permette a un utente di abbandonare il Team di appartenenza
     *
     * @param username l'username dell'utente che vuole abbandonare il proprio team
     * @return void
     * @throws Exception se l'utente non appartiene a nessun team, se il team è associato a un hackathon, o se l'utente non esiste
     */
    public void abbandonaTeam(String username) throws Exception{
        // recupero il team di appartentenza
        Team mioTeam = teamRep.findByUtenti_Username(username)
            .orElseThrow(()->new Exception("Errore: non appartieni a nessun team"));

        /* recupero l'hackathon a cui sono iscritto, se sono iscritto ad un hackathon
            non posso abbandonare il team */
        Hackathon hackathon = mioTeam.getHackathon();
        if(hackathon != null){
            throw new Exception("Errore: impossibile abbandonare il team");
        }

        // rimuovo l'utente dalla lista dei partecipanti del Team
        Utente utente = utenteRep.findByUsername(username)
                .orElseThrow(()->new Exception("Errore: non esiste un utente con questo nome"));

        mioTeam.removeUtente(utente);
        teamRep.save(mioTeam);
    }
}
