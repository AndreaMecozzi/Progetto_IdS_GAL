package unicam.ids2526.gal.progetto_hackhub_gal.application.handlers;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids2526.gal.progetto_hackhub_gal.core.hackathon.Hackathon;
import unicam.ids2526.gal.progetto_hackhub_gal.core.inviti.EsitoInvito;
import unicam.ids2526.gal.progetto_hackhub_gal.core.inviti.Invito;
import unicam.ids2526.gal.progetto_hackhub_gal.core.inviti.InvitoDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.core.team.Team;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.utenti.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.HackathonRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.InvitoRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.List;

/**
 * Gestisce la logica applicativa relativa agli inviti tra utenti e team
 *
 * Coordina le operazioni tra i repository di utenti, inviti e team,
 * garantendo la coerenza dei dati tramite gestione transazionale
 */
@Service
@Transactional
public class InvitoHandler {
    private final UtenteRepository userRep;
    private final InvitoRepository invitoRep;
    private final TeamRepository teamRep;

    /**
     * Inizializza il servizio con i repository necessari
     *
     * @param userRep   repository per l'accesso ai dati degli utenti
     * @param invitoRep repository per l'accesso ai dati degli inviti
     * @param teamRep   repository per l'accesso ai dati dei team
     */
    public InvitoHandler(UtenteRepository userRep, InvitoRepository invitoRep, TeamRepository teamRep) {
        this.userRep=userRep;
        this.invitoRep=invitoRep;
        this.teamRep=teamRep;
    }


    /**
     * Invita un utente a partire dal suo username
     *
     * @param userMittente l'utente che invia l'invito
     * @param userRicevente l'username dell'utente ricevente
     *
     * @throws RuntimeException se l'utente ricevente non esiste o è già membro di un team
     */
    public void invitaUtente(String userMittente, String userRicevente) throws Exception{

        if(userMittente.equals(userRicevente)){
            throw new Exception("Errore: Impossibile invitare se stessi");
        }

        Utente mittente=userRep.findByUsername(userMittente).orElseThrow();

        Utente ricevente=userRep.findByUsername(userRicevente).orElseThrow(
                ()->new Exception("Errore: Utente non trovato"));

        Team team = teamRep.findByUtenti_Username(userMittente).orElseThrow(
                ()->new Exception("Errore: Team di appartenenza non trovato"));

        Hackathon hackathon = team.getHackathon();

        if(hackathon.getStato()!="IN_ISCRIZIONE"){
            throw new Exception("Errore: Impossibile invitare, fase di iscrizione conclusa");
        }

        if(ricevente.getRuolo()!= Ruolo.UTENTE){
            throw  new Exception("Errore: È possibile invitare solo gli utenti");
        }

        if(invitoRep.existsByMittenteAndRicevente(mittente,ricevente)){
            throw new Exception("Errore: Utente già invitato");
        }

        if(teamRep.findByUtenti_Username(userMittente).isEmpty()){
            throw  new Exception("Errore: Devi creare prima un team!");
        }

        if (teamRep.findByUtenti_Username(userRicevente).isPresent()){
            throw  new Exception("Errore: L'Utente è già in un team");
        }

        Invito invito=new Invito(mittente,ricevente);
        invitoRep.save(invito);
    }


    /**
     * Restituisce la lista degli inviti ricevuti dall'utente
     *
     * @param username  l'username dell'utente
     * @return lista di inviti oppure null se non esistono
     *
     */

    public List<InvitoDTO> visualizzaInviti(String username) throws Exception {
        Utente utente = userRep.findByUsername(username)
                .orElseThrow(() -> new Exception("Utente non trovato"));

        // Recupera gli inviti (ad esempio quelli ricevuti)
        List<Invito> inviti = invitoRep.findByRicevente(utente);

        // Trasformazione in DTO
        return inviti.stream().map(invito -> new InvitoDTO(
                invito.getInvitoId(),
                invito.getMittente().getUsername(),
                invito.getMittente().getEmail(),
                invito.getMessaggio()
        )).toList();
    }

    /**
     * Gestisce l'accettazione o il rifiuto di un invito da parte di un utente.
     *
     * @param username l'utente che sta gestendo l'invito (deve essere il destinatario)
     * @param invitoId l'ID dell'invito da gestire
     * @param esito true se l'utente accetta, false se rifiuta
     * @throws Exception se l'invito non esiste, non appartiene all'utente o è già stato gestito
     */
    public void gestisciInvito(String username, Long invitoId, boolean esito) throws Exception {

        // recupero dell'invito tramite il suo Id
        Invito invito = invitoRep.findById(invitoId).orElseThrow(
                () -> new Exception("Errore: Invito non trovato"));

        // controllo sulla validità dell'invito
        if (invito.getEsitoInvito() != EsitoInvito.INVIATO) {
            throw new Exception("Errore: Questo invito è già stato gestito in precedenza.");
        }

        // controllo sullo stato dell'hackathon
        Team team = teamRep.findByUtenti_Username(invito.getMittente().getUsername()).orElseThrow(
                ()->new Exception("Errore: Team di appartenenza non trovato"));

        Hackathon hackathon = team.getHackathon();

        if(hackathon.getStato()!="IN_ISCRIZIONE"){
            throw new Exception("Errore: Impossibile accettare, fase di iscrizione conclusa");
        }

        // scelta dell'esito: accettato o rifiutato
        if (esito) {
            // aggiornato lo stato dell'invito in "ACCETTATO"
            invito.setEsitoInvito(EsitoInvito.ACCETTATO);

            // trasformato il ricevente in membro del team di cui ora fa parte
            Utente mittente = invito.getMittente();
            Team t = teamRep.findByUtenti_Username(mittente.getUsername()).orElseThrow(
                    ()->new Exception("Errore: L'utente non fa parte di un team"));

            // Prendo l'utente da inserire nel team
            Utente ricevente = invito.getRicevente();

            // Aggiungo l'utente alla lista dei membri del team
            t.addUtente(ricevente);

            teamRep.save(t);

        } else {
            // aggiornato lo stato dell'invito in "RIFIUTATO"
            invito.setEsitoInvito(EsitoInvito.RIFIUTATO);
        }

        invitoRep.save(invito);
    }
}
