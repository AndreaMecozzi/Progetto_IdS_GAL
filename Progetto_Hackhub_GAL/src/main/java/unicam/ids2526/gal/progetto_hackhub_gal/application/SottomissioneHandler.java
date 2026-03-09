package unicam.ids2526.gal.progetto_hackhub_gal.application;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Sottomissione;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.SottomissioneRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;

import java.io.File;

@Service
public class SottomissioneHandler {

    private final SottomissioneRepository sottomissioneRep;
    private final TeamRepository teamRep;

    public SottomissioneHandler(SottomissioneRepository sottomissioneRep, TeamRepository teamRep) {
        this.sottomissioneRep = sottomissioneRep;
        this.teamRep = teamRep;
    }

    /**
     * Crea una nuova sottomissione per un team specifico
     *
     * @param team l'oggetto Team che intende effettuare la sottomissione
     * @throws Exception se il team fornito non esiste nel database
     */

    public void creaSottomissione(Team team) throws Exception {
        Team t = teamRep.findById(team.getTeamId()).orElseThrow(
                () -> new Exception("Errore: Team non esistente"));

        // Creazione dell'entità sottomissione legata al team trovato
        Sottomissione sottomissione = new Sottomissione("Sottomissione - " + t.getNome(), t);
        sottomissioneRep.save(sottomissione);
    }

    /**
     * Aggiorna una sottomissione esistente aggiungendo o modificando il file allegato
     *
     * @param nomeTeam l'oggetto Team di cui si vuole aggiornare la sottomissione
     * @param file il nuovo documento/file da associare alla sottomissione
     * @throws Exception se non viene trovata alcuna sottomissione precedente per il team indicato
     */


    //AL POSTO DI FILE ANDREBBE CREATA UNA CLASSE "Document" PER POTER USARE @OneToOne, perche appunto
    //@OneToOne punta a un entità, cosa che java.io.File non è
    public void aggiornaSottomissione(String nomeTeam, File file) throws Exception {
        // Cerca la sottomissione esistente per il team
        Sottomissione sottomissione = sottomissioneRep.findByTeamNome(nomeTeam).orElseThrow(
                () -> new Exception("Errore: Nessuna sottomissione trovata per questo team"));

        sottomissione.setFile(file);
        sottomissioneRep.save(sottomissione);
    }

    /**
     * Restituisce la sottomissione effettuata da un team per un determinato hackathon
     *
     * @param nomeTeam l'oggetto Team che ha effettuato la sottomissione
     * @param nomeHackathon l'oggetto Hackathon a cui si riferisce la sottomissione
     * @return la sottomissione trovata
     * @throws Exception se non esiste una sottomissione che soddisfi entrambi i criteri
     */
    public Sottomissione visualizzaSottomissione(String nomeTeam, String nomeHackathon) throws Exception {
        return sottomissioneRep.findByTeamAndHackathon(nomeTeam, nomeHackathon).orElseThrow(
                () -> new Exception("Errore: Sottomissione non trovata per i parametri inseriti"));
    }
}

