package unicam.ids2526.gal.progetto_hackhub_gal.application;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.NoSuchElementException;

@Service
public class TeamHandler {
    private final TeamRepository teamRep;
    private final UtenteRepository utenteRep;

    public TeamHandler(TeamRepository teamRepository, UtenteRepository utenteRep) {
        this.teamRep = teamRepository;
        this.utenteRep = utenteRep;
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
