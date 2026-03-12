package unicam.ids2526.gal.progetto_hackhub_gal.application;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

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
}
