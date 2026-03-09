package unicam.ids2526.gal.progetto_hackhub_gal.application;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Invito;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Ruolo;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.InvitoRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

import java.util.List;

/**
 * Implementa la logica di business per le azione che possono essere svolte da un utente
 */
@Service
public class InvitoHandler {
    private final UtenteRepository userRep;
    private final InvitoRepository invitoRep;

    public InvitoHandler(UtenteRepository userRep, InvitoRepository invitoRep) {
        this.userRep=userRep;
        this.invitoRep=invitoRep;
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
        Utente mittente=userRep.findByUsername(userMittente).orElseThrow(
                ()->new Exception("Errore: Mittente non esistente")); /// ---> Mittente non esistente

        Utente ricevente=userRep.findByUsername(userRicevente).orElseThrow(
                ()->new Exception("Errore: Utente non trovato")); /// --> Utente non trovato

        if(mittente.getUserId().equals(ricevente.getUserId())){
            throw new Exception("Errore: Impossibile invitare se stessi"); /// --> Invito a se stessi
        }

        if(ricevente.getRuolo()!= Ruolo.UTENTE || mittente.getRuolo()!= Ruolo.UTENTE){
            throw  new Exception("Errore: Solo gli utenti possono invitare o essere invitati"); /// --> Non autorizzati
        }

        if(invitoRep.existsByMittenteAndRicevente(mittente,ricevente)){
            throw new Exception("Errore: Utente già invitato"); /// --> Invito utente già invitato
        }


        //TODO implementare controllo che mittente abbia un team
        //TODO implementare controllo del team tramite TeamRepository per controllare ricevente in un team

        Invito invito=new Invito(mittente,ricevente);
        invitoRep.save(invito);
    }


    /**
     * restituisce la lista degli inviti ricevuti dall'utente
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
