package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.supporto.Supporto;
import unicam.ids2526.gal.progetto_hackhub_gal.core.supporto.SupportoDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.TeamDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.HackathonRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.SupportoRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.List;

/**
 * Gestisce la logica applicativa relativa alle richieste di supporto
 *
 * Coordina le operazioni tra i repository di supporto, utenti, team e hackathon,
 * garantendo la coerenza dei dati tramite gestione transazionale
 */
@Service
@Transactional
public class SupportoHandler {
    private final SupportoRepository supportoRep;
    private final UtenteRepository utenteRep;
    private final TeamRepository teamRep;
    private final HackathonRepository hackathonRep;

    /**
     * Inizializza il servizio con i repository necessari
     *
     * @param supportoRep  repository per l'accesso ai dati delle richieste di supporto
     * @param utenteRep    repository per l'accesso ai dati degli utenti
     * @param teamRep      repository per l'accesso ai dati dei team
     * @param hackathonRep repository per l'accesso ai dati degli hackathon
     */
    public SupportoHandler(SupportoRepository supportoRep, UtenteRepository utenteRep,
                           TeamRepository teamRep, HackathonRepository hackathonRep) {
        this.supportoRep = supportoRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
        this.hackathonRep = hackathonRep;
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
        // recupero del team del richiedente
        Team team = teamRep.findByUtenti_Username(username).orElseThrow(
                () -> new Exception("Errore: Non appartieni a nessun team"));

        Hackathon hackathon = team.getHackathon();

        // controllo se il team sia iscritto a un hackathon
        if (hackathon == null) {
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

        // creazione e salvataggio della richiesta di supporto
        Supporto supporto = new Supporto(team, hackathon, richiesta);
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
    public List<SupportoDTO> visualizzaRichieste(String username) throws Exception {
        // recupero del richiedente e controllo il ruolo
        Utente richiedente = utenteRep.findByUsername(username).orElseThrow(
                () -> new Exception("Errore: Utente non trovato"));


        if (richiedente.getRuolo() != Ruolo.MENTORE) {
            throw new Exception("Errore: Non hai i permessi per visualizzare le richieste di supporto");
        }

        // recupero dell'hackathon gestito dal mentore
        Hackathon hackathon = hackathonRep.findByMentoriContaining(richiedente).orElseThrow(
                () -> new Exception("Errore: Hackathon non trovato"));

        // recupero le richieste di supporto indirizzate al mentore.
        List<Supporto> supporti = supportoRep.findByHackathon_HackathonID(hackathon.getHackathonID());

        if (supporti == null || supporti.isEmpty()) {
            throw new Exception("Errore: Non ci sono richieste di supporto");
        }

        // costruzione Dto
        return supporti.stream().map(supporto -> new SupportoDTO(
                supporto.getSupportoID(),
                supporto.getTeam().getNome(),
                supporto.getHackathon().getNome(),
                supporto.getRichiesta()
        )).toList();
    }

}
