package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.supporto.Supporto;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.SupportoRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.List;

@Service
public class SupportoHandler {
    private final SupportoRepository supportoRep;
    private final UtenteRepository utenteRep;
    private final TeamRepository teamRep;

    public SupportoHandler(SupportoRepository supportoRep, UtenteRepository utenteRep,
                           TeamRepository teamRep) {
        this.supportoRep = supportoRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
    }

    /**
     * Permette a un membro del team di richiedere supporto ai mentori dell'hackathon
     *
     * @param username  l'username del richiedente
     * @param richiesta il testo della richiesta di supporto
     * @throws Exception se l'utente non appartiene a nessun team o il team non è iscritto a un hackathon
     * @throws Exception se l'hackathon non è in stato IN_CORSO
     * @throws Exception se la richiesta è vuota
     */
    public void richiediSupporto(String username, String richiesta) throws Exception {
        // recupero il team del richiedente
        Team team = teamRep.findByUtenti_Username(username).orElseThrow(
                () -> new Exception("Errore: Non appartieni a nessun team"));

        List<Utente> membri = team.getUtenti();
        Hackathon hackathon = team.getHackathon();

        //       controllo che il richiedente sia nel team e che il team sia iscritto a un hackathon
        Utente richiedente = utenteRep.findByUsername(username).orElseThrow(
                () -> new Exception("Errore: Utente non trovato"));

        if (!membri.contains(richiedente) || hackathon == null) {
            throw new Exception("Errore: Impossibile richiedere supporto");
        }

        // controllo che l'hackathon sia in corso
        if (!hackathon.getStato().equals("IN_CORSO")) {
            throw new Exception("Errore: Impossibile richiedere supporto");
        }

        // controllo che la richiesta non sia vuota
        if (richiesta.isEmpty()) {
            throw new Exception("Errore: Richiesta vuota");
        }

        // recupero i mentori dell'hackathon
        List<Utente> mentori = hackathon.getMentori();

        // creazione e salvataggio della richiesta di supporto
        Supporto supporto = new Supporto(richiedente, mentori, richiesta);
        supportoRep.save(supporto);
    }

    /**
     * Permette a un mentore di visualizzare le richieste di supporto a lui indirizzate
     * @param username l'username del mentore

     * @return la lista delle richieste di supporto ricevute
     *
     *
     * @throws Exception se l'utente non possiede il ruolo MENTORE
     * @throws Exception se non ci sono richieste di supporto
     */
    public List<Supporto> visualizzaRichieste(String username) throws Exception {
        //      recupero il richiedente e controllo il ruolo
        Utente richiedente = utenteRep.findByUsername(username).orElseThrow(
                () -> new Exception("Errore: Utente non trovato"));


        if (richiedente.getRuolo() != Ruolo.MENTORE) {
            throw new Exception("Errore: Non hai i permessi per visualizzare le richieste di supporto");
        }

        // recupero le richieste di supporto indirizzate al mentore.
        List<Supporto> supporti = supportoRep.findByRiceventeContaining(richiedente);

        if (supporti == null || supporti.isEmpty()) {
            throw new Exception("Errore: Non ci sono richieste di supporto");
        }



        return supporti;
    }

}
