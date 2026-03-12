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
     *restituisce le info di un team dato il suo nome,
     * e verifica che l'utente che fa la richiesta sia membro del team
     *
     * @param username l'username dell'utente che fa la richiesta estratto dal JWT
     * @param nomeTeam il nome del team di cui vuoi visualizzare le info
     * @return team
     * @throws Exception se il team non esiste o l'utente non è membro del team
     */

    public Team visualizzaTeam(String username , String nomeTeam) throws Exception{
        Team team = teamRep.findByNome(nomeTeam)
                .orElseThrow(()->new Exception("team non trovato"));

        //verifica che l'utente che fa la richiesta sia membro del team
        /*
        team.getUtenti()  -> retituisce la lista degli utenti del team
        stream()          -> converto la lista in uno stream (cosi posso fare delle operazioni)
        anyMatch(... )    -> scorre gli utenti e controlla se il suo username è uguale a quello del JWT
                             se lo trova restituisce true
         */
        boolean isMembro = team.getUtenti().stream().anyMatch(u -> u.getUsername().equals(username));

        if (!isMembro) {
            throw new Exception("non sei un membro di questo team");
        }

        return team;

    }
}
