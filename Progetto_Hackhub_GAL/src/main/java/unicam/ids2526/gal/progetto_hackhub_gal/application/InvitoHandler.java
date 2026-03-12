package unicam.ids2526.gal.progetto_hackhub_gal.application;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Invito;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.InvitoRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.TeamRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.List;

/**
 * Implementa la logica di business per le azione che possono essere svolte da un utente
 */
@Service
public class InvitoHandler {
    private final UtenteRepository userRep;
    private final InvitoRepository invitoRep;
    private final TeamRepository teamRep;

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
            throw new Exception("Errore: Impossibile invitare se stessi"); /// --> Invito a se stessi
        }

        Utente mittente=userRep.findByUsername(userMittente).orElseThrow();

        Utente ricevente=userRep.findByUsername(userRicevente).orElseThrow(
                ()->new Exception("Errore: Utente non trovato")); /// --> Utente non trovato

        if(ricevente.getRuolo()!= Ruolo.UTENTE){
            throw  new Exception("Errore: È possibile invitare solo gli utenti"); /// --> Non autorizzati
        }

        if(invitoRep.existsByMittenteAndRicevente(mittente,ricevente)){
            throw new Exception("Errore: Utente già invitato"); /// --> Invito utente già invitato
        }

        if(teamRep.findByUtenti_Username(userMittente).isEmpty()){
            throw  new Exception("Errore: Devi creare prima un team!"); /// --> Il mittente non ha un team
        }

        if (teamRep.findByUtenti_Username(userRicevente).isPresent()){
            throw  new Exception("Errore: L'Utente è già in un team"); /// --> Il ricevente è già in un team
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

    public List<Invito> visualizzaInviti(String username) throws Exception{
        Utente utente = userRep.findByUsername(username).orElseThrow(
                ()->new Exception("Errore: Utente non trovato"));

        //chiama il metodo del repository e cerca tutti gli inviti dove il campo ricevente
        //corrisponde all'utente trovato
        List<Invito> inviti = invitoRep.findByRicevente(utente);


        if(inviti.isEmpty()){
            return null;
        }else{
            return inviti;
        }
    }
}
