package unicam.ids2526.gal.progetto_hackhub_gal.application;

import org.springframework.stereotype.Service;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Invito;
import unicam.ids2526.gal.progetto_hackhub_gal.core.Utente;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.InvitoRepository;
import unicam.ids2526.gal.progetto_hackhub_gal.infrastructure.UtenteRepository;

/**
 * Implementa la logica di business per le azione che possono essere svolte da un utente
 */
@Service
public class UtenteHandler {
    private final UtenteRepository userRep;
    private final InvitoRepository invitoRep;

    public UtenteHandler(UtenteRepository userRep, InvitoRepository invitoRep) {
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

        //TODO implementare controllo del team tramite TeamRepository

        Invito invito=new Invito(mittente,ricevente);
        invitoRep.save(invito);
    }
}
